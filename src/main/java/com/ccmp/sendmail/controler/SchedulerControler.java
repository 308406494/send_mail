package com.ccmp.sendmail.controler;

import com.ccmp.sendmail.bean.ExcelData;
import com.ccmp.sendmail.bean.InfoDto;
import com.ccmp.sendmail.config.feign.pltform.PlatformClient;
import com.ccmp.sendmail.config.feign.pltform.model.PltResponse;
import com.ccmp.sendmail.config.feign.pltform.model.TaskRequest;
import com.ccmp.sendmail.mapper.crm.CrmMapper;
import com.ccmp.sendmail.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping(value = "/scheduler",method = RequestMethod.POST)
public class SchedulerControler {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private PlatformClient platformClient;

    @Autowired
    private CrmMapper crmMapper;


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${scheduled.sendmail.toAddress}")
    private String toAddress;

    @Value("${scheduled.sendmail.fromAddress}")
    private String fromAddress;

    @Value("${spring.mail.enable}")
    private Boolean otherMailSendFlag;

    @Value("${spring.mail.filePath}")
    private String filePath;

    private ScheduledFuture future;

    private static Logger logger = LoggerFactory.getLogger(SchedulerControler.class);

    @Value("${scheduled.sendmail.cron}")
    private String sendCron;

    @PostMapping("/start")
    @ResponseBody
    public void startScheduler(){
        future = threadPoolTaskScheduler.schedule(new SendMailTask(platformClient,crmMapper, toAddress), new CronTrigger(sendCron));
    }

    @PostMapping("/stop")
    @ResponseBody
    public void stopScheduler(){
        if(future != null && !future.isCancelled()){
            future.cancel(true);
        }
    }
    @PostMapping("/change")
    @ResponseBody
    public void changeScheduler(@RequestParam(value = "cron", required = false) String cron, @RequestParam(value="sendAddress", required = false) String sendAddress){
        stopScheduler();
        if(cron == null){
            cron = this.sendCron;
        }
        if(sendAddress == null){
            sendAddress = this.toAddress;
        }
        future = threadPoolTaskScheduler.schedule(new SendMailTask(platformClient, crmMapper, sendAddress), new CronTrigger(cron));
    }


    class SendMailTask implements Runnable{

        private PlatformClient platformClient;

        private CrmMapper crmMapper;

        private String sendAddress;

        public SendMailTask(PlatformClient platformClient, CrmMapper crmMapper,String sendAddress) {
            this.platformClient = platformClient;
            this.crmMapper = crmMapper;
            this.sendAddress = sendAddress;
        }

        @Override
        public void run() {
            logger.info("收集需要发送的信息！！");
            List<InfoDto> infoReport = crmMapper.getInfoReport();
            String content = "统计报表|"+mailTemplate(infoReport);


            boolean platSend = platSend(platformClient, sendAddress, content, true);
            if(platSend)
                logger.info("邮件发送成功！！");
            else {
                if(otherMailSendFlag){
                    logger.info("开始调用自设置服务器发送邮件！！");
                    ExcelData excelData = ExcelUtils.converToExcelData(infoReport);
                    File file = new File(filePath+File.separator+"统计报表"+System.currentTimeMillis()+".xls");
                    FileOutputStream fos = null;
                    try{
                        fos = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ExcelUtils.exportExcel(excelData, fos);
                    sendSimpleMail(sendAddress,content, file);
                } else
                    logger.info("邮件发送失败！！");
            }
        }

        private String mailTemplate(List<InfoDto> infos){
            StringBuffer startContent = new StringBuffer("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<table>\n");

            StringBuffer titleContent = new StringBuffer("<tr><td>省份</td><td>集团名称</td><td>集团编码</td><td>可测试</td><td>已激活</td><td>其它</td><td>当日新增</td><td>当日已销户</td><td>合计用户数</td></tr>");
            String contentTempalte = titleContent.toString();
            for(InfoDto infoDto : infos){
                String content = contentTempalte.replaceAll("省份",infoDto.getProvince())
                        .replaceAll("集团名称",infoDto.getCustName())
                        .replaceAll("集团编码",infoDto.getCustCode())
                        .replaceAll("可测试",infoDto.getTestNum().toString())
                        .replaceAll("已激活",infoDto.getActiveNum().toString())
                        .replaceAll("其它",infoDto.getOtherNum().toString())
                        .replaceAll("当日新增",infoDto.getTodayNew().toString())
                        .replaceAll("当日已销户", infoDto.getTodayDis().toString())
                        .replaceAll("合计用户数",infoDto.getCount().toString());
                titleContent.append(content);
            }

            StringBuffer endContet = new StringBuffer("</table></body>\n" +
                    "</html>");

            return startContent.append(titleContent).append(endContet).toString();
        }



        private void sendSimpleMail(String sendAddress, String content,File file){
            MimeMessage message = null;
            try {
                message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(fromAddress);
                helper.setTo(sendAddress.trim());
                String[] lines = content.split("\\|");
                helper.setSubject(lines[0]);
                helper.setText(lines[1], true);
                logger.info(file.getName());
                helper.addAttachment(file.getName(), new FileSystemResource(file));
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        /**
         * 公共平台发送功能
         * @param platformClient
         * @param sendAddress
         * @param content
         * @param sendType true:邮件 false:短信
         * @return
         */
        private boolean platSend(PlatformClient platformClient, String sendAddress, String content, boolean sendType){
            TaskRequest taskRequest = new TaskRequest();
            taskRequest.setTemplateFlag(false);

            taskRequest.setAccessSysCode("CCMP_PORTAL");
            taskRequest.setSendTo(sendAddress);
            taskRequest.setPriority(0);
            if(sendType){
                String[] lines = content.split("\\|");
                taskRequest.setSubject(lines[0]);
                taskRequest.setTemplateCodeOrContent(lines[1]);
                taskRequest.setCcmpSeq("pltSendMail");
            } else {
                taskRequest.setCcmpSeq("pltSendSms");
                taskRequest.setTemplateCodeOrContent(content.replaceAll("<|>",""));
            }
            boolean sendFlag = false;
            Integer sendTime = 0;
            while(!(sendFlag || sendTime ==3) ){
                try {
                    logger.info("开始第"+(sendTime+1)+"次发送！");
                    PltResponse pltResponse = null;
                    if(sendType){
                        pltResponse = platformClient.mailSend(taskRequest);
                    } else {
                        pltResponse = platformClient.smsSend(taskRequest);
                    }
                    if(PltResponse.RST_SUCC.equals(pltResponse.getRespCode()))
                        sendFlag = true;
                    else
                        sendTime ++;
                } catch (Exception e) {
                    e.printStackTrace();
                    sendTime ++;
                }
            }
            return sendFlag;
        }
    }


}

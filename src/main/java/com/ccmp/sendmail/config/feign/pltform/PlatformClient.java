package com.ccmp.sendmail.config.feign.pltform;

import com.ccmp.sendmail.config.feign.pltform.model.PltResponse;
import com.ccmp.sendmail.config.feign.pltform.model.TaskRequest;
import feign.Headers;
import feign.RequestLine;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author yangtong
 * @date 2018/3/15 0015 16:39
 * @describe:
 */
@Service
@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface PlatformClient {
    /**
     * 发送短信
     * @param request
     * @return
     * @throws Exception
     */
    @RequestLine("POST /sms/send")
    PltResponse smsSend(@RequestBody TaskRequest request) throws Exception;

    /**
     * 发送邮件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestLine("POST /mail/send")
    PltResponse mailSend(@RequestBody TaskRequest request) throws Exception;

    /**
     * 发送API消息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestLine("POST /apimsg/send")
    PltResponse apiMsgSend(@RequestBody TaskRequest request) throws Exception;

}

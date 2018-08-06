package com.ccmp.sendmail.scheduled;

import com.ccmp.sendmail.controler.SchedulerControler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SendScheduled implements CommandLineRunner {

    @Autowired
    private SchedulerControler schedulerControler;

    private Logger logger = LoggerFactory.getLogger(SendScheduled.class);
    @Override
    public void run(String... args) throws Exception {
        logger.info("开始添加send定时任务！！");
        schedulerControler.startScheduler();
        logger.info("添加send定时任务成功！！");
    }
}

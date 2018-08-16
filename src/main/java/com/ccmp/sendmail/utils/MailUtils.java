package com.ccmp.sendmail.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

public class MailUtils {


    public static void sendMail(JavaMailSender javaMailSender,String sendAddress,String fromAddress, String content,File file){
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress);
            helper.setTo(sendAddress.trim());
            String[] lines = content.split("\\|");
            helper.setSubject(lines[0]);
            helper.setText(lines[1], true);
            helper.addAttachment(file.getName(), new FileSystemResource(file));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

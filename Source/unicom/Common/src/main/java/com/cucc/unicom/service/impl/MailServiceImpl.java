package com.cucc.unicom.service.impl;

import com.cucc.unicom.config.MailSenderConfig;
import com.cucc.unicom.mapper.MailLogMapper;
import com.cucc.unicom.mapper.MailMapper;
import com.cucc.unicom.pojo.MailConfigInfo;
import com.cucc.unicom.pojo.MailInfo;
import com.cucc.unicom.pojo.MailLog;
import com.cucc.unicom.component.MailProperties;
import com.cucc.unicom.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    MailSenderConfig mailSenderConfig;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    private MailLogMapper mailLogMapper;


    @Override
    public MailConfigInfo getMailSenderConfig() {
        MailConfigInfo mailConfigInfo = mailMapper.getMailSenderConfig();
        return mailConfigInfo;
    }

    @Override
    public void updateMailSenderConfig(MailConfigInfo mailConfigInfo) {
        mailMapper.updateMailSenderConfig(mailConfigInfo);
    }


    public void sendSimpleMail(MailInfo mailInfo) {
        mailSenderConfig.clear();
        JavaMailSenderImpl sender = mailSenderConfig.getSender();
        System.out.println("sender.getUsername() = " + sender.getUsername());
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(sender.getUsername());
            message.setTo(mailInfo.getDestination());
            message.setSubject(mailInfo.getSubject());
            message.setSentDate(new Date());
            message.setText(mailInfo.getText());
            CompletableFuture.runAsync(()->{
                try {
                    sender.send(message);
                    mailLogMapper.insertMailLog(new MailLog(mailInfo.getDestination(),mailInfo.getText(),true));
                } catch (MailException e) {
                    mailLogMapper.insertMailLog(new MailLog(mailInfo.getDestination(),mailInfo.getText(),false));
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

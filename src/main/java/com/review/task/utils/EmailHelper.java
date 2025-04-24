package com.review.task.utils;

import com.review.task.entity.ResetPassword;
import com.review.task.repository.ResetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailHelper {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String from;

    @Value("${forgetPassword.url}")
    private String url;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    public void sendSimpleEmail(String subject, String text,String username,String toEmail) {

        String uuid = UUID.randomUUID().toString();
        String link = url + "/" + uuid;
        ResetPassword resetPassword = new ResetPassword(uuid,username,System.currentTimeMillis()+ (5*60*1000),link);
        resetPasswordRepository.save(resetPassword);
        System.out.println(link);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(text);
//        message.setFrom(from);

//        mailSender.send(message);
    }
}
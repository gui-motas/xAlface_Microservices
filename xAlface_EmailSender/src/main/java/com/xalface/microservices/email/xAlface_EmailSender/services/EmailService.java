package com.xalface.microservices.email.xAlface_EmailSender.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import com.xalface.microservices.email.xAlface_EmailSender.DTOs.UserDTO;

@Service
public class EmailService {

    final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(UserDTO dto){

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        
        mailMessage.setTo(dto.username());
        mailMessage.setFrom(emailFrom);
        mailMessage.setSubject("Welcome to xAlface");
        mailMessage.setText("Hello " + dto.name() + ",\n\n" +
                             "Welcome to xAlface! Your account has been created successfully.\n" +
                             "Username: " + dto.username() + "\n" +
                             "Password: " + dto.password() + "\n\n" +
                             "Best regards,\n" +
                             "xAlface Team");

     
        mailSender.send(mailMessage);
        
        System.out.println("Email sent to: " + dto.username());
    }

}

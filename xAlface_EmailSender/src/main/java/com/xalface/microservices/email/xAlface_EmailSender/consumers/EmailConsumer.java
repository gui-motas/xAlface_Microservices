package com.xalface.microservices.email.xAlface_EmailSender.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.xalface.microservices.email.xAlface_EmailSender.DTOs.UserDTO;
import com.xalface.microservices.email.xAlface_EmailSender.services.EmailService;

@Component
public class EmailConsumer {
    
    @Autowired
    public EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload UserDTO dto) {
      System.out.println("Received message from email queue, sending email to:" + dto.username());
        emailService.sendEmail(dto);
    
    }
}

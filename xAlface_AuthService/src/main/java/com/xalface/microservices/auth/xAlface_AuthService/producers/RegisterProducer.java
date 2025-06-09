package com.xalface.microservices.auth.xAlface_AuthService.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xalface.microservices.auth.xAlface_AuthService.model.RegisterDTO;

@Component
public class RegisterProducer {

    final RabbitTemplate rabbitTemplate;

    public RegisterProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void sendMessage(RegisterDTO dto) {
        var registerDto = new RegisterDTO();
        registerDto.setName(dto.getName());
        registerDto.setUsername(dto.getUsername());
        registerDto.setPassword(dto.getPassword());
        registerDto.setDepartment(dto.getDepartment());
        registerDto.setRole(dto.getRole());
        
        rabbitTemplate.convertAndSend("", routingKey, registerDto);


    }

}

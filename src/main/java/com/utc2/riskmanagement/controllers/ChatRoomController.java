package com.utc2.riskmanagement.controllers;

import com.utc2.riskmanagement.payloads.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatRoomController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // Mapped as /app/private
    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getTo(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
}

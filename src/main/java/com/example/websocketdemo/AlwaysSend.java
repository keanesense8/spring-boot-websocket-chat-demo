package com.example.websocketdemo;

import com.example.websocketdemo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Service
public class AlwaysSend {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @PostConstruct
    public void init() throws InterruptedException {
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setType(ChatMessage.MessageType.LEAVE);
                chatMessage.setSender(Instant.now().toString());

                messagingTemplate.convertAndSend("/topic/public", chatMessage);
            }
        }).start();

    }


}

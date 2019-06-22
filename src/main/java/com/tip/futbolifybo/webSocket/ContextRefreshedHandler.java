package com.tip.futbolifybo.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedHandler implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            SendService.setTemplate(template);
        } catch (Exception ex) {
            System.out.println("SendService can not created!");
        }
    }
}

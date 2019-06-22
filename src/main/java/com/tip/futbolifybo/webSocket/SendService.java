package com.tip.futbolifybo.webSocket;

import com.tip.futbolifybo.api.response.PollResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SendService {

    private static SimpMessagingTemplate template;

    public static void setTemplate(SimpMessagingTemplate tmplt) {
        template = tmplt;
    }

    public static void sendPoll(String path, PollResponse poll) {
        template.convertAndSend(path, poll);
    }
}

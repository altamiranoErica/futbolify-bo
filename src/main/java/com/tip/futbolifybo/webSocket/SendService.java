package com.tip.futbolifybo.webSocket;

import com.tip.futbolifybo.api.response.PollResponse;
import com.tip.futbolifybo.api.response.VenueResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SendService {

    private static SimpMessagingTemplate template;

    public static void setTemplate(SimpMessagingTemplate tmplt) {
        template = tmplt;
    }

    public static void sendPoll(String path, PollResponse poll) {
        template.convertAndSend(path, poll);
    }

    public static void sendVenue(String path, VenueResponse venue) {
        template.convertAndSend(path, venue);
    }
}

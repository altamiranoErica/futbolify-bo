package com.tip.futbolifybo.webSocket;

import com.tip.futbolifybo.api.response.PollResponse;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private static final String SENDING_NEW_POLL_EVENT = "/poll/new_event";
    private static final String SENDING_FINISH_POLL_EVENT = "/poll/finish_event";

    @SubscribeMapping(SENDING_NEW_POLL_EVENT)
    public PollResponse onSubscribeNewPollEvent(PollResponse poll) {
        return poll;
    }

    @SubscribeMapping(SENDING_FINISH_POLL_EVENT)
    public PollResponse onSubscribeFinishPollEvent(PollResponse poll) {
        return poll;
    }
}

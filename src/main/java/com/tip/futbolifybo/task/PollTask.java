package com.tip.futbolifybo.task;


import com.tip.futbolifybo.model.Poll;
import com.tip.futbolifybo.service.PollService;

public class PollTask implements Runnable {

    private Poll poll;
    private PollService pollService;

    public PollTask(PollService pollService, Poll currentPoll) {
        this.pollService = pollService;
        this.poll = currentPoll;
    }

    @Override
    public void run() {
        this.pollService.finish(poll);
    }
}

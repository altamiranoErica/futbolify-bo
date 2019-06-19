package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.PollResponse;
import com.tip.futbolifybo.model.Track;
import com.tip.futbolifybo.model.Venue;
import com.tip.futbolifybo.model.Poll;
import com.tip.futbolifybo.repository.TrackRepository;
import com.tip.futbolifybo.repository.VenueRepository;
import com.tip.futbolifybo.repository.PollRepository;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.TrackResult;
import com.tip.futbolifybo.service.result.PollResult;
import com.tip.futbolifybo.task.PollTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PollService implements JSONMapperUtil<PollResult>{

    @Autowired
    protected SpotifyProviderService providerService;

    @Autowired
    protected VenueRepository venueRepository;

    @Autowired
    protected PollRepository pollRepository;

    @Autowired
    protected TrackRepository trackRepository;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    public GenericResponse add(String json) {
        PollResult pollResult = this.getResultFromJSON(json, PollResult.class);
        if(pollResult == null){
            return new GenericResponse("ERROR", "Can not read DATA!");
        }

        if(pollResult.getTracks().isEmpty() || pollResult.getTracks().size() < 2){
            return new GenericResponse("ERROR", "You must choose at least two tracks!");
        }

        Optional<Venue> _venue = this.venueRepository.findById(UUID.fromString(pollResult.getVenueID()));
        if (!_venue.isPresent()){
            return null;
        }

        Venue venue = _venue.get();

        TrackResult currentTrack = this.providerService.getCurrentTrack(venue);
        if(currentTrack == null) {
            return new GenericResponse("ERROR", "They're not listening to anything now!");
        }

        Poll poll = new Poll();
        poll.setVenue(venue);

        for (TrackResult trackResult : pollResult.getTracks()) {
            poll.getTracks().add(new Track(trackResult.getId(), trackResult.getName(), trackResult.getCode()));
        }

        Integer expireTime = currentTrack.getDuration() - currentTrack.getProgressMS();

        poll.setExpireTime(expireTime);
        poll = this.pollRepository.save(poll);

        taskScheduler.schedule(new PollTask(this, poll), new Date(System.currentTimeMillis() + expireTime));

        return new GenericResponse("SUCCESS", "Successfully created poll!");
    }

    public List<PollResponse> list() {
        Iterable<Poll> polls = this.pollRepository.findAllByActiveTrue();
        List<PollResponse> result = new ArrayList<>();
        for (Poll poll : polls) {
            result.add(new PollResponse(poll));
        }

        return result;
    }

    public GenericResponse vote(String pollID, String trackID) {
        Track track = this.trackRepository.findByProviderIDAndPAndPoll_PollID(trackID, UUID.fromString(pollID));
        if (track == null){
            return new GenericResponse("ERROR", "Selected track does not exist!");
        }

        track.addVote();

        this.trackRepository.save(track);
        return new GenericResponse("SUCCESS", "Successful voting added!");
    }

    public void finish(Poll poll) {
        poll.setActive(false);
        this.pollRepository.save(poll);
    }
}

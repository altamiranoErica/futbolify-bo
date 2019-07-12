package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.PollResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.api.response.VenueResponse;
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
import com.tip.futbolifybo.webSocket.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.*;

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

        TrackResult currentTrack = this.providerService.getCurrentTrackWithPositionIntoPlaylist(venue);
        if(currentTrack == null) {
            return new GenericResponse("ERROR", "They're not listening to anything now!");
        }

        Poll poll = new Poll();
        poll.setVenue(venue);
        poll.setAutomatic(pollResult.getAutomatic());

        for (TrackResult trackResult : pollResult.getTracks()) {
            poll.getTracks().add(new Track(trackResult.getId(), trackResult.getName(),
                    trackResult.getArtist(), trackResult.getCode(), trackResult.getPosition()));
        }

        Integer expireTime = currentTrack.getDuration() - currentTrack.getProgressMS() - 7000;

        poll.setExpireTime(expireTime);
        poll.setPositionWinnerTrack(currentTrack.getPosition() + 1);

        poll = this.pollRepository.save(poll);

        taskScheduler.schedule(new PollTask(this, poll), new Date(System.currentTimeMillis() + expireTime));

        // ----------------> NOTIFICAR INICIO DE VOTACIÓN
        SendService.sendPoll("/poll/start_event", new PollResponse(poll, expireTime));

        return new GenericResponse("SUCCESS", "Successfully created poll!");
    }

    public List<PollResponse> list() {
        Iterable<Poll> polls = this.pollRepository.findAllByActiveTrue();
        List<PollResponse> result = new ArrayList<>();
        for (Poll poll : polls) {
            result.add(new PollResponse(poll, this.calculateExpirationTime(poll)));
        }

        return result;
    }

    private Integer calculateExpirationTime(Poll poll) {
        Long expirationTime = poll.getCreateTime().getTime() + poll.getExpireTime() - System.currentTimeMillis();
        return expirationTime.intValue();
    }

    public PollResponse vote(String pollID, String trackID) {
        Track track = this.trackRepository.findByProviderIDAndPAndPoll_PollID(trackID, UUID.fromString(pollID));
        if (track == null){
            return null;
        }

        track.addVote();

        this.trackRepository.save(track);

        Optional<Poll> _poll = this.pollRepository.findById(UUID.fromString(pollID));
        if (!_poll.isPresent()){
            return null;
        }

        Poll poll = _poll.get();
        PollResponse response = new PollResponse().makePartialResultPoll(poll);

        return response;
    }

    public void finish(Poll poll) {
        Track track = this.getWinnerTrack(poll.getPollID());
        if(track == null) return;

        // ----------------> FINALIZAR VOTACIÓN
        poll.setActive(false);
        this.pollRepository.save(poll);

        // ----------------> AGREGAR O REORDENAR PISTA GANADORA
        Boolean response;

        if(poll.getAutomatic()){
            response = this.providerService.reorderPlaylistsTracks(poll.getVenue(), track.getOriginalPosition(), poll.getPositionWinnerTrack());
        } else {
            response = this.providerService.addTrackToPlaylist(poll.getVenue(), track.getProviderID(), poll.getPositionWinnerTrack());
        }

        if(!response){
            System.out.println("ERROR AL AGREGAR PISTA A LA LISTA!\n");
        }

        // ----------------> NOTIFICAR FIN DE VOTACIÓN
        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(poll.getVenue().getStringVenueID());
        venueResponse.setTrack(new TrackResponse(track));

        SendService.sendVenue("/poll/finish_event", venueResponse);
    }

    private Track getWinnerTrack(UUID pollID) {
        List<Track> tracks = this.trackRepository.findTracksByPoll_PollIDOrderByNumberOfVotes(pollID);
        if (tracks == null || tracks.isEmpty()){
            return null;
        }

        return tracks.get(0);
    }
}

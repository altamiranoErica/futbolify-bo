package com.tip.futbolifybo.api.response;

import com.tip.futbolifybo.model.Track;
import com.tip.futbolifybo.model.Poll;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PollResponse {

    private String id;
    private String venueID;
    private List<TrackResponse> tracks;

    private Integer expirationTime;

    public PollResponse() {
        this.tracks = new ArrayList<>();
    }

    public PollResponse(Poll poll) {
        this();
        this.id = poll.getStringPollID();
        this.venueID = poll.getVenue().getStringVenueID();
        this.makeTrackList(poll.getTracks());
    }

    public PollResponse(Poll poll, Integer expireTime) {
        this(poll);
        this.expirationTime = expireTime;
    }

    private void makeTrackList(List<Track> tracks) {
        for (Track track : tracks) {
            this.tracks.add(new TrackResponse(track.getProviderID(), track.getName(), track.getArtist(), track.getImage()));
        }
    }

    public PollResponse makePartialResultPoll(Poll poll){
        this.id = poll.getStringPollID();
        for (Track track : poll.getTracks()) {
            TrackResponse trackResponse = new TrackResponse(track.getProviderID(), track.getName(), track.getArtist(), track.getImage());
            trackResponse.setVotesCount(track.getNumberOfVotes());

            this.tracks.add(trackResponse);
        }

        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public List<TrackResponse> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackResponse> tracks) {
        this.tracks = tracks;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }
}

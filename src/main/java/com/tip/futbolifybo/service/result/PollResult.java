package com.tip.futbolifybo.service.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PollResult {

    private String venueID;
    private List<TrackResult> tracks;
    private String expirationTime;

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public List<TrackResult> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackResult> tracks) {
        this.tracks = tracks;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }
}

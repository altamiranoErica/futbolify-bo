package com.tip.futbolifybo.service.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PollResult {

    private String venueID;
    private List<TrackResult> tracks;
    private String expirationTime;
    private Boolean automatic;

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

    public Boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }
}

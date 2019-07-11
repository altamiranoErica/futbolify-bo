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

    public PollResponse(Poll poll) {
        this.id = poll.getStringPollID();
        this.venueID = poll.getVenue().getStringVenueID();
        this.makeTrackList(poll.getTracks());
    }

    private void makeTrackList(List<Track> tracks) {
        this.tracks = new ArrayList<>();
        for (Track track : tracks) {
            this.tracks.add(new TrackResponse(track.getProviderID(), track.getName(), track.getArtist(), track.getImage()));
        }
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
}

package com.tip.futbolifybo.api.response;

import com.tip.futbolifybo.model.Venue;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class VenueResponse {

    private String id;
    private String name;
    private String image;

    private TrackResponse track;

    public VenueResponse(Venue venue) {
        this.id = venue.getStringVenueID();
        this.name = venue.getName();
        this.image = venue.getImage();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TrackResponse getTrack() {
        return track;
    }

    public void setTrack(TrackResponse track) {
        this.track = track;
    }
}

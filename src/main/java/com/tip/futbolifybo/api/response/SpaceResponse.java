package com.tip.futbolifybo.api.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class SpaceResponse {

    private String id;
    private String name;

    private TrackResponse track;

    public SpaceResponse() { }

    public SpaceResponse(UUID spaceID, String name) {
        this.id = spaceID.toString();
        this.name = name;
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

    public TrackResponse getTrack() {
        return track;
    }

    public void setTrack(TrackResponse track) {
        this.track = track;
    }
}

package com.tip.futbolifybo.api.response;

import com.tip.futbolifybo.model.Track;
import com.tip.futbolifybo.service.result.TrackResult;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrackResponse {

    private String name;
    private String id;
    private String code;
    private String artist;
    private String album;
    private Boolean isPlaying;
    private Integer progressMS;

    private Integer votesCount;
    private Integer position;

    public TrackResponse() { }

    public TrackResponse(TrackResult result) {
        this.name = result.getName();
        this.id = result.getId();
        this.code = result.getCode();
        this.artist = result.getArtist();
        this.album = result.getAlbumName();
        this.isPlaying = result.getIsPlaying();
        this.progressMS = result.getProgressMS();
        this.position = result.getPosition();
    }

    public TrackResponse(String providerID, String name, String artist, String image) {
        this.id = providerID;
        this.name = name;
        this.artist = artist;
        this.code = image;
    }

    public TrackResponse(Track track) {
        this.isPlaying = true;
        this.id = track.getProviderID();
        this.code = "https://open.spotify.com/embed?uri=spotify:track:" + track.getProviderID();
        this.name = track.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public Integer getProgressMS() {
        return progressMS;
    }

    public void setProgressMS(Integer progressMS) {
        this.progressMS = progressMS;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}

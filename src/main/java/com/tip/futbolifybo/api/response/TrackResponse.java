package com.tip.futbolifybo.api.response;

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

    public TrackResponse() { }

    public TrackResponse(TrackResult result) {
        this.name = result.getName();
        this.id = result.getId();
        this.code = result.getEmbedCode();
        this.artist = result.getArtist();
        this.album = result.getAlbumName();
        this.isPlaying = result.getIsPlaying();
        this.progressMS = result.getProgressMS();
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
}

package com.tip.futbolifybo.service.result;

import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Track;

public class TrackResult {

    private String name;
    private String uri;
    private String id;
    private String albumID;
    private String albumName;
    private String artist;
    private String embedCode;
    private Boolean isPlaying;
    private Integer progressMS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public void setEmbedCode(String embedCode) {
        this.embedCode = embedCode;
    }

    public Boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public Integer getProgressMS() {
        return progressMS;
    }

    public void setProgressMS(Integer progressMS) {
        this.progressMS = progressMS;
    }

    public TrackResult loadFewInfo(Track track) {
        this.setName(track.getName());
        this.setUri(track.getUri());
        this.setId(track.getId());
        this.setAlbumID(track.getAlbum().getId());
        this.setAlbumName(track.getAlbum().getName());

        Image[] images = track.getAlbum().getImages();
        if(images.length > 0) this.setEmbedCode(images[images.length - 1].getUrl());

        ArtistSimplified[] artists = track.getArtists();
        if(artists != null && artists.length > 0) this.setArtist(artists[0].getName());

        return this;
    }
}

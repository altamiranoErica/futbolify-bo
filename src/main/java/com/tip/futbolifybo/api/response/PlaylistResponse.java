package com.tip.futbolifybo.api.response;

import com.tip.futbolifybo.service.result.PlaylistResult;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlaylistResponse {

    private String playlistID;
    private String name;

    public PlaylistResponse() { }

    public PlaylistResponse(PlaylistResult playlist) {
        this.playlistID = playlist.getPlaylistID();
        this.name = playlist.getName();
    }

    public String getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

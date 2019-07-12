package com.tip.futbolifybo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tracks")
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID trackID;

    private String providerID;

    private String name;

    private String artist;

    private String image;

    private Integer numberOfVotes;

    private Integer originalPosition;

    public Track(){
        this.numberOfVotes = 0;
    }

    public Track(String providerID, String name, String artist, String image, Integer position) {
        this();
        this.providerID = providerID;
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.originalPosition = position;
    }

    public UUID getTrackID() {
        return trackID;
    }

    public void setTrackID(UUID trackID) {
        this.trackID = trackID;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Integer numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public void addVote() {
        this.numberOfVotes += 1;
    }

    public Integer getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(Integer originalPosition) {
        this.originalPosition = originalPosition;
    }
}

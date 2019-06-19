package com.tip.futbolifybo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "polls")
public class Poll implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pollID;

    private Integer expireTime;

    private Timestamp createTime;

    private Boolean active;

    @ManyToOne()
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @OneToMany(cascade =  CascadeType.ALL)
    @JoinColumn(name="poll_id")
    private List<Track> tracks;

    public Poll() {
        this.tracks = new ArrayList<>();
        this.active = true;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public UUID getPollID() {
        return pollID;
    }

    public String getStringPollID() {
        return pollID.toString();
    }

    public void setPollID(UUID pollID) {
        this.pollID = pollID;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

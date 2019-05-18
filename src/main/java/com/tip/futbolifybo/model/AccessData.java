package com.tip.futbolifybo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "access_data")
public class AccessData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID dataID;

    private String key;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    public AccessData() { }

    public AccessData(String key, String value, Venue venue) {
        this.key = key;
        this.value = value;
        this.venue = venue;
    }

    public UUID getDataID() {
        return dataID;
    }

    public void setDataID(UUID dataID) {
        this.dataID = dataID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}

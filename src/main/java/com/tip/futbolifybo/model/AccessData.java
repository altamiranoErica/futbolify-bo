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
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    public AccessData() { }

    public AccessData(String key, String value, Space space) {
        this.key = key;
        this.value = value;
        this.space = space;
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

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}

package com.tip.futbolifybo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Domain model that represents a user.
 *
 * @author cassiomolin
 */
@Entity
@Table(name = "venues")
public class Venue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID venueID;

    private String name;

    private String provider;

    @OneToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "venue")
    private List<AccessData> accessData;

    public Venue() { }

    public Venue(String name, String provider) {
        this.name = name;
        this.provider = provider;
    }

    public UUID getVenueID() {
        return venueID;
    }

    public void setVenueID(UUID venueID) {
        this.venueID = venueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<AccessData> getAccessData() {
        return accessData;
    }

    public void setAccessData(List<AccessData> accessData) {
        this.accessData = accessData;
    }

    public AccessData getAccessData(String key) {
        return this.accessData.stream()
                .filter(data -> data.getKey().equals(key))
                .findFirst().orElse(null);

    }
}

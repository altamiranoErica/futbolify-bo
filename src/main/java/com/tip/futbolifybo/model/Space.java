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
@Table(name = "spaces")
public class Space implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID spaceID;

    private String name;

    private String provider;

    @OneToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "space")
    private List<AccessData> accessData;

    public Space() { }

    public Space(String name, String provider) {
        this.name = name;
        this.provider = provider;
    }

    public UUID getSpaceID() {
        return spaceID;
    }

    public void setSpaceID(UUID spaceID) {
        this.spaceID = spaceID;
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

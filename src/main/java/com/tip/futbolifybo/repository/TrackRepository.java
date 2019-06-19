package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.Track;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrackRepository extends CrudRepository<Track, UUID> {

    @Query("SELECT t FROM Poll p INNER JOIN p.tracks t WHERE t.providerID = ?1 AND p.pollID = ?2")
    Track findByProviderIDAndPAndPoll_PollID(String providerID, UUID pollID);
}

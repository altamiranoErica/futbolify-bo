package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VenueRepository extends CrudRepository<Venue, UUID> {


}

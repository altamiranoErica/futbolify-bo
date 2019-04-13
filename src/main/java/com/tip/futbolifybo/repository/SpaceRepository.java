package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.Space;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {


}

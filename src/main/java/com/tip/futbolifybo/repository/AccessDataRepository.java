package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.AccessData;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccessDataRepository extends CrudRepository<AccessData, UUID> {


}

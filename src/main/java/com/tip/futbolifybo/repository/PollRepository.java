package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PollRepository extends CrudRepository<Poll, UUID> {

    List<Poll> findAllByActiveTrue();
}

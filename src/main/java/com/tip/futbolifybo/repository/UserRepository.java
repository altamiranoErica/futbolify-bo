package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findUserByUsername(String username);
}

package com.tip.futbolifybo.repository;

import com.tip.futbolifybo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String identifier);

}

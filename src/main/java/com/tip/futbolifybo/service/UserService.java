package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.SpaceResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.model.Authority;
import com.tip.futbolifybo.model.Space;
import com.tip.futbolifybo.model.User;
import com.tip.futbolifybo.repository.SpaceRepository;
import com.tip.futbolifybo.repository.UserRepository;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.TrackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public GenericResponse add(String username, String _password) {
        User user = new User();
        String password = passwordEncoder.encode(_password);
        user.setUsername(username);
        user.setPassword(password);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(Authority.ADMIN);
        authorities.add(Authority.USER);
        user.setAuthorities(authorities);

        userRepository.save(user);

        return new GenericResponse("SUCCESS", "Successfully created user!");
    }
}

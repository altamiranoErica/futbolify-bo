package com.tip.futbolifybo.security.service.impl;

import com.tip.futbolifybo.model.Authority;
import com.tip.futbolifybo.model.User;
import com.tip.futbolifybo.repository.UserRepository;
import com.tip.futbolifybo.security.api.AuthenticatedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link UserDetailsService}.
 *
 * @author cassiomolin
 */

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails.Builder()
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withActive(user.getActive())
                .withAuthorities(mapToGrantedAuthorities(user.getAuthorities()))
                .build();

        return authenticatedUserDetails;
    }

    private Set<GrantedAuthority> mapToGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .collect(Collectors.toSet());
    }
}
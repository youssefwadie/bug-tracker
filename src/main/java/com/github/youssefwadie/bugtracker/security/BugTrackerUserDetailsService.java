package com.github.youssefwadie.bugtracker.security;


import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BugTrackerUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = repository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            log.error("No user name with email: {} was founded.", email);
            throw new UsernameNotFoundException("No user name with email: %s was founded.".formatted(email));
        }
        log.info("{} is trying to login.", email);
        return new BugTrackerUserDetails(userByEmail.get());
    }
}

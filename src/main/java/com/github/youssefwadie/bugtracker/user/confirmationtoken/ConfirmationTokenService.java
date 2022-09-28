package com.github.youssefwadie.bugtracker.user.confirmationtoken;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.youssefwadie.bugtracker.model.ConfirmationToken;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public ConfirmationToken save(ConfirmationToken token) {
        return repository.save(token);
    }


    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    public void deleteAllByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }

    public void setConfirmedById(Long id) {
        repository.setEnabledById(id);
    }
}

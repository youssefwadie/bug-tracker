package com.github.youssefwadie.bugtracker.user.services;

import com.github.youssefwadie.bugtracker.user.dao.UserRepository;
import com.github.youssefwadie.bugtracker.model.ConfirmationToken;
import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.confirmationtoken.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final Role DEFAULT_USER_ROLE = Role.ROLE_DEVELOPER;
    private static final int EXPIRES_AFTER_MINUTES = 15;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidatorService validatorService;

    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Register a user registration request
     *
     * @param user the new user details
     * @return the confirmation token for the user email
     * @throws ConstraintsViolationException if the given user's email or password is invalid
     */
    @Transactional
    public String singUpUser(User user) throws ConstraintsViolationException {
        user.setRole(DEFAULT_USER_ROLE);
        final User savedUser = this.save(user);
        return generateConfirmationToken(savedUser.getId());
    }

    @Transactional
    public String regenerateConfirmationCode(Long userId) {
        return generateConfirmationToken(userId);
    }

    private String generateConfirmationToken(Long userId) {
        final String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        ConfirmationToken confirmationToken = new ConfirmationToken(null,
                token,
                false,
                now,
                now.plusMinutes(EXPIRES_AFTER_MINUTES),
                userId);

        ConfirmationToken savedConfirmationToken = confirmationTokenService.save(confirmationToken);

        return savedConfirmationToken.getToken();
    }

    @Transactional
    public User save(User user) throws ConstraintsViolationException {
        validatorService.validateUser(user);

        if (user.getId() == null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    public void enableUserById(Long userId) {
        userRepository.enableUserById(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean existsAllByIds(List<Long> ids) {
        return ids.stream().allMatch(userRepository::existsById);
    }


    public Long countTeamMembersByProjectId(Long projectId) {
        return userRepository.countTeamMembersByProjectId(projectId);
    }

    public Page<User> findAllTeamMembers(Long projectId, Pageable pageable) {
        return userRepository.findAllTeamMembers(projectId, pageable);
    }
}

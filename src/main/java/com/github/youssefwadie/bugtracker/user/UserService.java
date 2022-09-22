package com.github.youssefwadie.bugtracker.user;

import com.github.youssefwadie.bugtracker.model.ConfirmationToken;
import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.user.confirmationtoken.ConfirmationTokenService;
import com.github.youssefwadie.bugtracker.user.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final Role DEFAULT_USER_ROLE = Role.DEVELOPER_ROLE;
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
        final String token = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();
        ConfirmationToken confirmationToken = new ConfirmationToken(null,
                token,
                false,
                now,
                now.plusMinutes(EXPIRES_AFTER_MINUTES),
                savedUser.getId());

        ConfirmationToken savedConfirmationToken = confirmationTokenService.save(confirmationToken);

        // TODO: send confirmation mail
        // TODO: build confirmation url
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
}

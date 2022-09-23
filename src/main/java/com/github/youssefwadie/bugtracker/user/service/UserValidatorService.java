package com.github.youssefwadie.bugtracker.user.service;

import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintViolationException;
import com.github.youssefwadie.bugtracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

// TODO: validate name
@RequiredArgsConstructor
@Service
public class UserValidatorService {
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,16}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    public static final String PASSWORD_VALIDATION_MSG = """
            The password must contains at least 8 characters and at most 16 characters
            \t1-at least one digit.
            \t2-one lowercase alphabet.
            \t3-one uppercase alphabet.
            \t4-least one special character which includes !@#$%&*()-+=^.
            \t5-doesn't contain any white space.
            """;

    public static final String BLANK_INPUT_MSG = "cannot be blank";
    public static final String ALREADY_TAKEN_EMAIL_MSG = "already taken";
    public static final String INVALID_EMAIL_MSG = "invalid email";
    private final EmailValidator emailValidator = EmailValidator.getInstance(true);
    private final UserRepository userRepository;

    public boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean isValidEmail(String email) {
        return emailValidator.isValid(email);
    }

//    public void validateRegistrationRequest(RegistrationRequest registrationRequest) throws ConstraintViolationException {
//        final Map<String, String> errors = new HashMap<>();
//        String email = registrationRequest.getEmail();
//        if (isBlank(email)) {
//            errors.put("email", BLANK_INPUT_MSG);
//        } else if (!emailAlreadyInUser(registrationRequest.getEmail())) {
//            errors.put("email", ALREADY_TAKEN_EMAIL_MSG);
//        } else if (!isValidEmail(registrationRequest.getEmail())) {
//            errors.put("email", INVALID_EMAIL_MSG);
//        }
//
//        if (!isValidPassword(registrationRequest.getPassword())) {
//            errors.put("password", PASSWORD_VALIDATION_MSG);
//        }
//        if (isBlank(registrationRequest.getFirstName())) {
//            errors.put("firstName", BLANK_INPUT_MSG);
//        }
//        if (isBlank(registrationRequest.getLastName())) {
//            errors.put("lastName", BLANK_INPUT_MSG);
//        }
//        if (!errors.isEmpty()) {
//            throw new ConstraintViolationException(errors);
//        }
//    }

    public boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    public void validateUser(User user) throws ConstraintViolationException {
        final Map<String, String> errors = new HashMap<>();
        final String userEmail = user.getEmail();
        if (!isValidEmail(userEmail)) {
            errors.put("email", INVALID_EMAIL_MSG);
        } else {
            if (user.getId() != null) {
                // NOT a new user
                if (!isUniqueEmail(user)) errors.put("email", ALREADY_TAKEN_EMAIL_MSG);
            } else {
                if (userRepository.existsByEmail(userEmail)) errors.put("email", ALREADY_TAKEN_EMAIL_MSG);
            }
        }

        // new user
        if (user.getId() == null) {
            if (!isValidPassword(user.getPassword())) {
                errors.put("password", PASSWORD_VALIDATION_MSG);
            }
        }

        if (isBlank(user.getFirstName())) {
            errors.put("firstName", BLANK_INPUT_MSG);
        }

        if (isBlank(user.getLastName())) {
            errors.put("lastName", BLANK_INPUT_MSG);
        }

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }

//    private boolean emailAlreadyInUser(String email) {
//        return userRepository.existsByEmail(email);
//    }

    private boolean isUniqueEmail(User user) {
        String userEmail = user.getEmail();
        Optional<User> userOptionalInDB = userRepository.findById(user.getId());

        // unlikely to happen
        if (userOptionalInDB.isEmpty()) {
            throw new IllegalArgumentException("the passed user's id is not in the database");
        }

        User userInTheDB = userOptionalInDB.get();

        // email changed
        if (!userInTheDB.getEmail().equals(user.getEmail())) {
            return !userRepository.existsByEmail(userEmail);
        }

        return true;
    }

}

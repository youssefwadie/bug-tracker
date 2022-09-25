package com.github.youssefwadie.bugtracker.user.service;

import com.github.youssefwadie.bugtracker.email.EmailSender;
import com.github.youssefwadie.bugtracker.model.ConfirmationToken;
import com.github.youssefwadie.bugtracker.model.RegistrationRequest;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.user.UserService;
import com.github.youssefwadie.bugtracker.user.confirmationtoken.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final static String EMAIL_CONFIRMATION_HTML_TEMPLATE = """
            <!DOCTYPE html><meta charset=utf-8><meta content="ie=edge"http-equiv=x-ua-compatible><title>Email Confirmation</title><meta content="width=device-width,initial-scale=1"name=viewport><style>@media screen{@font-face{font-family:'Source Sans Pro';font-style:normal;font-weight:400;src:local('Source Sans Pro Regular'),local('SourceSansPro-Regular'),url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff')}@font-face{font-family:'Source Sans Pro';font-style:normal;font-weight:700;src:local('Source Sans Pro Bold'),local('SourceSansPro-Bold'),url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff')}}a,body,table,td{-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}table,td{mso-table-rspace:0;mso-table-lspace:0}img{-ms-interpolation-mode:bicubic}a[x-apple-data-detectors]{font-family:inherit!important;font-size:inherit!important;font-weight:inherit!important;line-height:inherit!important;color:inherit!important;text-decoration:none!important}div[style*="margin: 16px 0;"]{margin:0!important}body{width:100%!important;height:100%!important;padding:0!important;margin:0!important}table{border-collapse:collapse!important}a{color:#1a82e2}img{height:auto;line-height:100%;text-decoration:none;border:0;outline:0}</style><body style=background-color:#e9ecef><table border=0 cellpadding=0 cellspacing=0 width=100%><tr><td align=center bgcolor=#e9ecef><table border=0 cellpadding=0 cellspacing=0 width=100% style=max-width:600px><tr><td align=left bgcolor=#ffffff style="padding:36px 24px 0;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;border-top:3px solid #d4dadf"><h1 style=margin:0;font-size:32px;font-weight:700;letter-spacing:-1px;line-height:48px>Confirm Your Email Address</h1></table><tr><td align=center bgcolor=#e9ecef><table border=0 cellpadding=0 cellspacing=0 width=100% style=max-width:600px><tr><td align=left bgcolor=#ffffff style="padding:24px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;line-height:24px"><p style=margin:0>Tap the button below to confirm your email address. If you didn't create an account on <a href=https://www.domain-name.io>Bug Tracker</a>, you can safely delete this email.<tr><td align=left bgcolor=#ffffff><table border=0 cellpadding=0 cellspacing=0 width=100%><tr><td align=center bgcolor=#ffffff style=padding:12px><table border=0 cellpadding=0 cellspacing=0><tr><td align=center bgcolor=#1a82e2 style=border-radius:6px><a href={{link}} target=_blank style="display:inline-block;padding:16px 36px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;color:#fff;text-decoration:none;border-radius:6px">Confirm</a></table></table><tr><td align=left bgcolor=#ffffff style="padding:24px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;line-height:24px"><p style=margin:0>If that doesn't work, copy and paste the following link in your browser:<p style=margin:0><a href=https://www.domain-name.io target=_blank>{{link}}</a></table><tr><td align=center bgcolor=#e9ecef style=padding:24px><table border=0 cellpadding=0 cellspacing=0 width=100% style=max-width:600px><tr><td align=center bgcolor=#e9ecef style="padding:12px 24px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:14px;line-height:20px;color:#666"><p style=margin:0>You received this email because we received a request for creating for an account on <a href=https://www.domain-name.io>App</a>. If you didn't request creating an account you can safely delete this email.</table></table>
             """;
    private final static String LINK_REPLACEMENT_REGEX = "\\{\\{link\\}\\}";
    private final static Pattern LINK_REPLACEMENT_PATTERN = Pattern.compile(LINK_REPLACEMENT_REGEX);

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    /**
     * Register a user registration request
     *
     * @param registrationRequest registration details
     * @return the confirmation url for the user email
     * @throws ConstraintsViolationException if the given email or password (in registrationRequest) is invalid
     */
    public String register(RegistrationRequest registrationRequest) {
        final User newUser = new User();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(registrationRequest.getPassword());
        newUser.setFirstName(registrationRequest.getFirstName());
        newUser.setLastName(registrationRequest.getLastName());

        String confirmationToken = userService.singUpUser(newUser);

        String link = "http://localhost:8080/api/v1/register/confirm?token=%s".formatted(confirmationToken);
        emailSender.send("Confirm your email", buildEmail(link), registrationRequest.getEmail());

        return confirmationToken;
    }

    public String resendConfirmationToken(User user) {
        String confirmationToken = userService.regenerateConfirmationCode(user.getId());
        String link = "http://localhost:8080/api/v1/register/confirm?token=%s".formatted(confirmationToken);
        emailSender.send("Confirm your email", buildEmail(link), user.getEmail());
        return confirmationToken;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken =
                confirmationTokenService.getToken(token)
                        .orElseThrow(() -> new IllegalArgumentException("token not found"));
        if (confirmationToken.isConfirmed()) {
            throw new IllegalArgumentException("email is already confirmed");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(now)) throw new IllegalArgumentException("token expired");

        confirmationTokenService.setConfirmedById(confirmationToken.getId());
        userService.enableUserById(confirmationToken.getUserId());
        return "confirmed";
    }


    private String buildEmail(String link) {
        return LINK_REPLACEMENT_PATTERN.matcher(EMAIL_CONFIRMATION_HTML_TEMPLATE).replaceAll(link);
    }
}

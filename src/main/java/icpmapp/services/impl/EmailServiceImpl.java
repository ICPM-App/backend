package icpmapp.services.impl;

import icpmapp.dto.requests.EmailRequest;
import lombok.RequiredArgsConstructor;
import icpmapp.services.EmailService;
import icpmapp.services.JWTService;
import icpmapp.services.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private final JWTService jwtService;
    private final UserService userService;

    public void sendSignup(EmailRequest emailRequest) throws AccessDeniedException {
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(emailRequest.getReceiver());
        boolean hasRequiredRole = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("INACTIVE"));


        if (!hasRequiredRole) {
            throw new AccessDeniedException("User already activated.");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@api-icpm.compute.dtu.dk");
        message.setTo(emailRequest.getReceiver());
        message.setSubject("ICPM app account activation");
        String token =  jwtService.generateToken(userDetails);
        message.setText("To activate your account for the ICPM app, click on the following link: https://icpm.compute.dtu.dk/icpm-navigator/#/auth/register/" + token);
        mailSender.send(message);
    }

    public void sendResetPassword(EmailRequest emailRequest) throws AccessDeniedException {
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(emailRequest.getReceiver());
        boolean hasWrongRole = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("INACTIVE"));


        if (hasWrongRole) {
            throw new AccessDeniedException("User needs to be activated");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@api-icpm.compute.dtu.dk");
        message.setTo(emailRequest.getReceiver());
        message.setSubject("Reset password");
        String token =  jwtService.generateToken(userDetails);
        message.setText("To reset your ICPM app account password, click on the following link: https://icpm.compute.dtu.dk/icpm-navigator/#/auth/login/resetpassword/" + token);
        mailSender.send(message);
    }
}

package com.richstone.cargo.event.listener;

import com.richstone.cargo.event.RegistrationCompleteEvent;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final DriverServiceImpl driverService;
    private final JavaMailSender mailSender;
    private User theUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        driverService.saveUserVerificationToken(theUser, verificationToken);
        String url = event.getApplicationUrl() + "/api/v1/driver/confirmEmail?token=" + verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Service";
        String mailContent = "<p> Hi, " + theUser.getFirstname().toUpperCase() + ", </p>" +
                "<p>Thank you for choosing us," + "" +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> User Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}

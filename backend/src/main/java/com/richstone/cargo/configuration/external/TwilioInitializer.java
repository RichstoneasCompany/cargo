package com.richstone.cargo.configuration.external;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TwilioInitializer {

    private final TwilioConfig twilioConfig;

    public TwilioInitializer(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }
}
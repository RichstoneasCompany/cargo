package com.richstone.cargo.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Bean
    FirebaseMessaging firebaseMessaging()throws IOException {
            GoogleCredentials googleCredentials =GoogleCredentials.fromStream(
                    new ClassPathResource("firebaseconfig/firebase-service-account.json").getInputStream());
            FirebaseOptions firebaseOptions =FirebaseOptions.builder().setCredentials(googleCredentials).build();
            FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "cargo-app");
            return FirebaseMessaging.getInstance(app);
    }


}
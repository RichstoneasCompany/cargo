package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.exception.DriverNotFoundException;
import com.richstone.cargo.model.*;
import com.richstone.cargo.model.types.Gender;
import com.richstone.cargo.model.types.Role;
import com.richstone.cargo.repository.DeviceTokenRepository;
import com.richstone.cargo.repository.DriverRepository;
import com.richstone.cargo.repository.VerificationTokenRepository;
import com.richstone.cargo.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;
    private final UserServiceImpl userService;
    private final VerificationTokenRepository tokenRepository;
    private final TripServiceImpl tripService;
    private final DeviceTokenRepository deviceTokenRepository;
    private final OtpServiceImpl otpService;

    @Value("${api.2gis.key}")
    private String API_2GIS_KEY;
    private static final String TYPE = "car";


    @Override
    public Driver addDriver(UserDto request) {
        log.info("Registering user: {}", request.getUsername());
        var user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(""))
                .role(Role.DRIVER)
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .gender(Gender.valueOf(request.getGender()))
                .build();
        userService.save(user);
        var driver = Driver.builder().user(user).build();
        driverRepository.save(driver);
        return driver;
    }

    public void registerDriver(RegistrationDto user) {
        log.info("Registering driver: {}", user.getPhone());
        User userByPhone = userService.findByPhone(user.getPhone());
        userByPhone.setEnabled(true);
        userByPhone.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.save(userByPhone);
        DeviceToken deviceToken = DeviceToken.builder()
                .userId(savedUser.getId())
                .token(user.getDeviceToken())
                .build();
        deviceTokenRepository.save(deviceToken);
        log.info("Driver registered successfully: {}", user.getPhone());
    }
    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        log.info("Saving verification token for user: {}", theUser.getUsername());
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
        log.info("Verification token saved successfully for user: {}", theUser.getUsername());
    }

    @Override
    public String validateToken(String theToken) {
        log.info("Validating verification token: {}", theToken);
        VerificationToken token = tokenRepository.findByToken(theToken);
        if (token == null) {
            log.warn("Invalid verification token: {}", theToken);
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            log.warn("Token already expired: {}", theToken);
            tokenRepository.delete(token);
            return "Token already expired";
        }
        log.info("Verification token validated successfully for user: {}", user.getUsername());
        user.setEnabled(true);
        userService.save(user);
        return "valid";
    }

    public Driver findByUserId(Long id) {
        log.info("Finding driver by userId: {}", id);
        Driver driver = driverRepository.findByUserId(id)
                .orElseThrow(() -> {
                    log.error("Driver not found with this id: {}", id);
                    return new DriverNotFoundException("Driver not found with this id: " + id, new DriverNotFoundException());
                });
        log.info("Driver found with id:  {}", id);
        return driver;
    }

    public RouteResponse buildRoute(Long id) {
        Driver driver = findByUserId(id);
        Trip trip = tripService.findInProgressTripByAssignedDriver(driver);
        Route route = trip.getRoute();

        Coordinates start = route.getStartLocationId().getCoordinates();
        Coordinates end = route.getEndLocationId().getCoordinates();

        String uri = String.format("https://2gis.ru/routeSearch/rsType/%s/from/%f,%f/to/%f,%f?key=%s",
                TYPE, start.getLongitude(), start.getLatitude(), end.getLongitude(), end.getLatitude(), API_2GIS_KEY);
        log.info("Constructed route URL: " + uri);
        return new RouteResponse(uri);
    }

    public void markDriverAsDeleted(User user) {
        Driver driver = driverRepository.findDriverByUser(user);
        if (driver != null) {
            driver.getUser().setDeleted(true);
            userService.save(driver.getUser());
            log.info("Driver marked as deleted: {}", driver.getId());
        } else {
            log.warn("Driver not found for user: {}", user.getId());
        }
    }

    public AuthenticationResponse loginDriver(DriverLoginDto request) throws AuthenticationException {
        User user = userService.findByPhone(request.getPhone());

        if (user != null && (passwordEncoder.matches(request.getPassword(), user.getPassword()))) {
            return otpService.generateJwt(user.getId());

        } else {
        throw new AuthenticationException("Invalid phone number or password");
    }
    }

    public Driver findById(Long id) {
        log.info("Finding driver by id: {}", id);
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Driver not found with this id: {}", id);
                    return new DriverNotFoundException("Driver not found with this id: " + id);
                });
        log.info("Driver found with id:  {}", id);
        return driver;
    }

    public void markDriverAsUndeleted(User user) {
        log.info("Undeleting driver for user: {}", user.getId());
        Driver driver = driverRepository.findDriverByUser(user);
        if (driver != null) {
            driver.getUser().setDeleted(false);
            driverRepository.save(driver);
            log.info("Driver undeleted successfully: {}", driver.getId());
        } else {
            log.warn("Driver not found for user: {}", user.getId());
        }
    }

    public void save(Driver driver) {
        driverRepository.save(driver);
        log.info("Driver saved successfully: {}", driver.getId());
    }
    public void delete(User user) {
        log.info("Deleting driver for user: {}", user.getId());
        Driver driver = driverRepository.findDriverByUser(user);
        if (driver != null) {
            tripService.deleteAllTripsByDriver(driver);
            driverRepository.delete(driver);
            userService.delete(driver.getUser());
            log.info("Driver deleted successfully: {}", driver.getId());
        } else {
            log.warn("Driver not found for user: {}", user.getId());
        }
    }

}

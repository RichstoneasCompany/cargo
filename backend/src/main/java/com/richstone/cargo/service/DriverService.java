package com.richstone.cargo.service;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.User;

public interface DriverService {
    Driver addDriver(UserDto request);
    void saveUserVerificationToken(User theUser, String token);
    String validateToken(String theToken);
}

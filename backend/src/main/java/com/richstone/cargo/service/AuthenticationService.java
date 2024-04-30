package com.richstone.cargo.service;

import com.richstone.cargo.dto.LoginDto;
import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.User;


public interface AuthenticationService {
//    public User register(UserDto request);

    public AuthenticationResponse authenticate(LoginDto request);
}

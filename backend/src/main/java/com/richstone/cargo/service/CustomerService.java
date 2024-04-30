package com.richstone.cargo.service;

import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.User;

public interface CustomerService {
    AuthenticationResponse registerCustomer(UserDto request);

}

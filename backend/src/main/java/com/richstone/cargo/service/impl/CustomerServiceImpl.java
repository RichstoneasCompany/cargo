package com.richstone.cargo.service.impl;

import com.richstone.cargo.configuration.JwtService;
import com.richstone.cargo.dto.AuthenticationResponse;
import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.Customer;
import com.richstone.cargo.model.Token;
import com.richstone.cargo.model.User;
import com.richstone.cargo.model.types.Role;
import com.richstone.cargo.model.types.TokenType;
import com.richstone.cargo.repository.CustomerRepository;
import com.richstone.cargo.repository.TokenRepository;
import com.richstone.cargo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final CustomerRepository customerRepository;

    @Override
    public AuthenticationResponse registerCustomer(UserDto request) {
        log.info("Registering customer: {}", request.getUsername());
        var user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .isEnabled(true)
                .build();
        User savedUser = userService.save(user);
        var customer = Customer.builder().user(user).build();
        customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        log.info("Customer registered successfully: {}", savedUser.getUsername());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        log.info("Saving token for user: {}", user.getUsername());
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        log.info("Token saved successfully for user: {}", user.getUsername());
    }
}

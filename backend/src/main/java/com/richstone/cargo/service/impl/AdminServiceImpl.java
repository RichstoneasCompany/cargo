package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.exception.DispatcherAlreadyExistException;
import com.richstone.cargo.model.User;
import com.richstone.cargo.model.types.Role;
import com.richstone.cargo.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerAdmin(UserDto userDto) {
        log.info("AdminServiceImpl::registering admin: {}", userDto.getUsername());
        User admin = User.builder()
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.ADMIN)
                .isEnabled(true)
                .build();
        User savedAdmin = userService.save(admin);
        log.info("AdminServiceImpl::admin registered successfully: {}", savedAdmin.getUsername());
        return savedAdmin;
    }

    @Override
    public User addDispatcher(UserDto userDto) {
        if (userService.findByUsernameOptional(userDto.getUsername()).isPresent()) {
            log.error("AdminServiceImpl::dispatcher with the same name already exists: {}", userDto.getUsername());
            throw new DispatcherAlreadyExistException("A dispatcher with the same name already exists");
        }
        log.info("Adding dispatcher: {}", userDto.getUsername());
        var user = User.builder()
                .username(userDto.getUsername())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.DISPATCHER)
                .isEnabled(true)
                .build();
        User savedDispatcher = userService.save(user);
        log.info("AdminServiceImpl::dispatcher added successfully: {}", savedDispatcher.getUsername());
        return savedDispatcher;
    }
}

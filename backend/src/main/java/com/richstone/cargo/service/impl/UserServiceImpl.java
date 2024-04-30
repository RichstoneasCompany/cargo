package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.UserDetailsDto;
import com.richstone.cargo.exception.DispatcherNotFoundException;
import com.richstone.cargo.exception.DriverNotFoundException;
import com.richstone.cargo.mapper.UserMapper;
import com.richstone.cargo.model.User;
import com.richstone.cargo.repository.UserRepository;
import com.richstone.cargo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("Username not found with username " + username);
                });

        log.info("Found user with username: {}", username);
        return user;

    }

    public Optional<User> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPhone(String phone) {
        log.info("Finding user by phone: {}", phone);

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> {
                    log.error("User not found with phone: {}", phone);
                    return new UsernameNotFoundException("Username not found with this phone " + phone);
                });

        log.info("Found user with phone: {}", phone);
        return user;
    }

    @Override
    public User findById(Long id) {
        log.info("Finding user by ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UsernameNotFoundException("User not found with this id" + id);
                });

        log.info("Found user with ID: {}", id);
        return user;
    }

    @Override
    public User save(User user) {
        log.info("Saving user: {}", user.getUsername());
        User savedUser = userRepository.save(user);
        log.info("User saved successfully: {}", savedUser.getUsername());
        return savedUser;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User is not valid");
                });
        log.info("Loaded user by username: {}", username);
        return user;
    }

    public UserDetailsDto updateUser(UserDetailsDto userDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        log.info("Updating user with ID: {}", user.getId());

        user.setFirstname(user.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        log.info("User updated successfully");

        return UserMapper.INSTANCE.toDto(user);
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        log.debug("Getting user by principal with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
        log.debug("User found by principal: {}", user.getUsername());

        return user;
    }

    public UserDetailsDto getUserDtoByPrincipal(Principal principal) {
        String username = principal.getName();
        log.debug("Getting user by principal with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
        log.debug("User found by principal: {}", user.getUsername());

        return UserMapper.INSTANCE.toDto(user);
    }

    public List<User> getInactiveDrivers() {
        return userRepository.findInactiveDrivers()
                .orElseThrow(() -> new DriverNotFoundException("Inactive drivers not found ", new DriverNotFoundException()));
    }

    public List<User> getAllDispatchers() {
        return userRepository.findDispatchers()
                .orElseThrow(() -> new DispatcherNotFoundException("Dispatchers not found ", new DispatcherNotFoundException()));
    }

    public List<User> getAllDrivers() {
        return userRepository.findDrivers()
                .orElseThrow(() -> new DriverNotFoundException("Driver not found ", new DriverNotFoundException()));
    }

    public void delete(User user) {
        log.info("Deleting user: {}", user.getUsername());
        userRepository.delete(user);
        log.info("User deleted successfully: {}", user.getUsername());
    }
    public List<User> getDeletedDrivers() {
        return userRepository.findDeletedDrivers()
                .orElseThrow(() -> new DriverNotFoundException("Deleted drivers not found ", new DriverNotFoundException()));
    }
}

package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.VerifyDto;
import com.richstone.cargo.exception.VerificationErrorException;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.DispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DispatcherServiceImpl implements DispatcherService {
    private final UserServiceImpl userService;

    @Override
    public User verifyDriver(VerifyDto dto) {
        log.info("Verifying driver: {}", dto.getUsername());

        try {
            User user = userService.findByUsername(dto.getUsername());
            user.setPhone(dto.getPhone());
            userService.save(user);
            log.info("Driver verified successfully: {}", user.getUsername());
            return user;
        } catch (Exception e) {
            log.error("Error verifying driver: {}", dto.getUsername(), e);
            throw new VerificationErrorException("Error verifying driver");
        }
    }
}




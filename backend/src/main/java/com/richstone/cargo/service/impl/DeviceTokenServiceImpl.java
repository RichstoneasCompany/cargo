package com.richstone.cargo.service.impl;

import com.richstone.cargo.exception.DriverNotFoundException;
import com.richstone.cargo.model.DeviceToken;
import com.richstone.cargo.model.Driver;
import com.richstone.cargo.repository.DeviceTokenRepository;
import com.richstone.cargo.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceTokenServiceImpl implements DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;

    public DeviceToken findDeviceTokenUserId(Long id) {
        log.info("Finding device token by userId: {}", id);
        DeviceToken deviceToken = deviceTokenRepository.findDeviceTokenByUserId(id)
                .orElseThrow(() -> {
                    log.error("User not found with this id: {}", id);
                    return new DriverNotFoundException("User not found with this id: " + id, new DriverNotFoundException());
                });
        log.info("Device token found for user id:  {}", id);
        return deviceToken;
    }
}

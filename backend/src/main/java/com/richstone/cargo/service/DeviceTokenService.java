package com.richstone.cargo.service;

import com.richstone.cargo.model.DeviceToken;

public interface DeviceTokenService {
    DeviceToken findDeviceTokenUserId(Long id);
}

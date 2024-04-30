package com.richstone.cargo.service;


import com.richstone.cargo.dto.VerifyDto;
import com.richstone.cargo.model.User;

public interface DispatcherService {
    User verifyDriver(VerifyDto dto);
}

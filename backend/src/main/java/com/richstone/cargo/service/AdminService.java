package com.richstone.cargo.service;


import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.User;

public interface AdminService {
    public User registerAdmin(UserDto userDto);

    User addDispatcher(UserDto userDto);
}

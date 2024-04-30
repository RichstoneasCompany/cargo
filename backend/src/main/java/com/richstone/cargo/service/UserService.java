package com.richstone.cargo.service;


import com.richstone.cargo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User findByUsername(String email);

    User findByPhone(String phone);

    User findById(Long userId);



    User save(User user);

    User loadUserByUsername(String username);


}

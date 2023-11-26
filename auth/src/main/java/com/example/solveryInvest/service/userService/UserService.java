package com.example.solveryInvest.service.userService;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.User;

public interface UserService {

    UserDto findByEmail(String email);

    boolean emailExists(String email);

    User findUserByEmail(String email);

    User save(UserDto userDto);
}

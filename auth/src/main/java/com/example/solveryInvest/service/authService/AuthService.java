package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.AuthDto;
import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse register(UserDto userDto);

    AuthDto authenticate(UserDto userDto);
}

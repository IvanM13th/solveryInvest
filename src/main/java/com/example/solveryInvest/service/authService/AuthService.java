package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationRequest;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import com.example.solveryInvest.entity.auth.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(UserDto userDto);

    AuthenticationResponse authenticate(UserDto userDto);
}

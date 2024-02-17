package com.example.solveryInvest.service.authService;

import com.example.solveryInvest.dto.AuthDto;
import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    AuthDto register(HttpServletRequest request, HttpServletResponse response, UserDto userDto);

    AuthDto authenticate(HttpServletRequest request, HttpServletResponse response, UserDto userDto);
}

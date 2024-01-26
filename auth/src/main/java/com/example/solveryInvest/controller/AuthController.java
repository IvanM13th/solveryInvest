package com.example.solveryInvest.controller;

import com.example.solveryInvest.dto.AuthDto;
import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import com.example.solveryInvest.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> register (@RequestBody UserDto userDto) {
        return CompletableFuture.supplyAsync(() -> authService.register(userDto))
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/authenticate")
    public CompletableFuture<ResponseEntity<AuthDto>> authenticate (@RequestBody UserDto userDto) {
        return CompletableFuture.supplyAsync(() -> authService.authenticate(userDto))
                .thenApply(ResponseEntity::ok);
    }
}

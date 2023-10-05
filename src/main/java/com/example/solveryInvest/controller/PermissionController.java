package com.example.solveryInvest.controller;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.entity.auth.AuthenticationResponse;
import com.example.solveryInvest.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/v1/permit")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    @GetMapping()
    public CompletableFuture<ResponseEntity<String>> sayHello() {
        return CompletableFuture.supplyAsync(() -> "Hello world from secured endpoint")
                .thenApply(ResponseEntity::ok);
    }
}

package com.example.solveryInvest.controller;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public CompletableFuture<ResponseEntity<UserDto>> findByEmail(@RequestParam String email) {
        return CompletableFuture.supplyAsync(() -> userService.findByEmail(email))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/id")
    public CompletableFuture<ResponseEntity<UserDto>> findById(@RequestParam Long id) {
        return CompletableFuture.supplyAsync(() -> userService.findById(id))
                .thenApply(ResponseEntity::ok);
    }
}

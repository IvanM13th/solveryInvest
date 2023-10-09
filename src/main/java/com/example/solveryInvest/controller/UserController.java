package com.example.solveryInvest.controller;

import com.example.solveryInvest.dto.UserDto;
import com.example.solveryInvest.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

package com.example.solveryInvest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/v1/permit")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    @GetMapping()
    public CompletableFuture<ResponseEntity<String>> saapiyHello() {
        return CompletableFuture.supplyAsync(() -> "Hello world from secured endpoint")
                .thenApply(ResponseEntity::ok);
    }
}

package com.example.solveryInvest.hazelcast;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping()
    public CompletableFuture<ResponseEntity<String>> checkSession(HttpServletRequest request) {
        return CompletableFuture.supplyAsync(() -> sessionService.getSession(request))
                .thenApply(ResponseEntity::ok);
    }
}

package solveryinvest.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.service.BalanceService;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> createBalance(@AuthenticationPrincipal User user) {
        return CompletableFuture.supplyAsync(() -> balanceService.createBalance(user))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> findByUserId(@AuthenticationPrincipal User user) {
        return CompletableFuture.supplyAsync(() -> balanceService.getBalanceDto(user.getId()))
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> updateBalance(@AuthenticationPrincipal User user,
                                                                       @RequestParam BigDecimal amount,
                                                                       @RequestParam OperationType type) {
        return CompletableFuture.supplyAsync(() -> balanceService.updateBalance(user.getId(), amount, type))
                .thenApply(ResponseEntity::ok);
    }
}

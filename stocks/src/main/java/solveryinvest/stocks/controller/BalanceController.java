package solveryinvest.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.service.BalanceService;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> createBalance(@RequestParam Long id) {
        return CompletableFuture.supplyAsync(() -> balanceService.createBalance(id))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> findByUserId(@RequestParam Long id) {
        return CompletableFuture.supplyAsync(() -> balanceService.getBalance(id))
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<BalanceDto>> updateBalance(@RequestParam Long id,
                                                                       @RequestParam Double amount,
                                                                       @RequestParam OperationType type) {
        return CompletableFuture.supplyAsync(() -> balanceService.updateBalance(id, amount, type))
                .thenApply(ResponseEntity::ok);
    }
}

package solveryinvest.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.service.PortfolioService;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public CompletableFuture<ResponseEntity<PortfolioDto>> createPortfolio(@RequestBody PortfolioDto portfolioDto) {
        return CompletableFuture.supplyAsync(() -> portfolioService.createPortfolio(portfolioDto))
                .thenApply(ResponseEntity::ok);
    }
}

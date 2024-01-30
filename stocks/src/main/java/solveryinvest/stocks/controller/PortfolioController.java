package solveryinvest.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.service.PortfolioService;

import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public CompletableFuture<ResponseEntity<PortfolioDto>> createPortfolio(@AuthenticationPrincipal User user,
                                                                           @RequestBody PortfolioDto portfolioDto) {
        return CompletableFuture.supplyAsync(() -> portfolioService.createPortfolio(user, portfolioDto))
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<AssetDto>> updatePortfolio(@RequestParam Long portfolioId,
                                                                       @RequestBody AssetDto assetDto) {
        return CompletableFuture.supplyAsync(() -> portfolioService.updatePortfolio(portfolioId, assetDto))
                .thenApply(ResponseEntity::ok);
    }
}

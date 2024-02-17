package solveryinvest.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PageDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.filters.FilterList;
import solveryinvest.stocks.filters.SortType;
import solveryinvest.stocks.service.AssetService;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.Array;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/asset")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @PostMapping
    public CompletableFuture<ResponseEntity<PageDto<AssetDto>>> getAssets(@RequestBody(required = false) List<FilterList> filters,
                                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                          @RequestParam(value = "field", required = false, defaultValue = "name") String field,
                                                                          @RequestParam(value = "direction", required = false, defaultValue = "DESC") SortType direction) {
        return CompletableFuture.supplyAsync(() -> assetService.findAllByFilters(filters, page, pageSize, field, direction))
                .thenApply(ResponseEntity::ok);
    }
}

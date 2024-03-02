package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PortfolioAssetDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.*;
import solveryinvest.stocks.enums.AssetOperationType;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.exception.AlreadyExistsException;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.PortfolioRepository;
import solveryinvest.stocks.service.*;
import solveryinvest.stocks.utils.BigDecimalsUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("portfolio-service")
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository pr;
    private final BalanceService bs;
    private final PortfolioAssetHistoryService pahs;
    private final ModelMapper mm;

    private final AssetService assetService;

    private final PortfolioAssetService pas;

    @Override
    @Transactional
    public PortfolioDto createPortfolio(User user, PortfolioDto portfolioDto) {
        validateSinglePortfolio(user);
        portfolioDto.setUserId(user.getId());
        var portfolio = mm.map(portfolioDto, Portfolio.class);
        pr.save(portfolio);
        return mm.map(portfolio, PortfolioDto.class);
    }

    @Override
    @Transactional
    public AssetDto updatePortfolio(User user, AssetDto assetDto) {
        var portfolio = pr.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Portfolio for user with id %s not found", user.getId())));
        var portfolioId = portfolio.getId();
        var portfolioAssets = pas.findByPortfolioId(portfolioId);
        var type = assetDto.getType();
        var balance = bs.getBalance(user.getId());
        validateBalance(portfolioAssets, balance, assetDto);
        pas.updateAssetsInPortfolio(portfolioAssets, portfolioId, assetDto);
        bs.updateBalanceWhenBuyOrSell(balance, assetDto.getType(), assetDto.getVolume());
        pahs.save(PortfolioAssetHistory.builder()
                .portfolioId(portfolioId)
                .assetId(assetDto.getId())
                .dateTime(OffsetDateTime.now())
                .lots(assetDto.getLots())
                .volume(assetDto.getVolume())
                .purchasePrice(assetDto.getPurchasePrice())
                .operationType(type)
                .build()
        );
        return assetDto;
    }

    @Override
    @Transactional
    public PortfolioDto getByUserId(User user) {
        var portfolio = pr.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Portfolio for user with id %s not found", user.getId())));
        var assets = pas.findByPortfolioId(portfolio.getId());
        var lotQuantityMap = assets.stream().map(PortfolioAsset::getAsset).collect(Collectors.toMap(Asset::getFigi, Asset::getLot));
        double portfolioProfit = 0.0;
        List<AssetDto> assetDtos = new ArrayList<>();
        BigDecimal portfolioValue = assets.stream()
                .map(asset -> BigDecimalsUtils.multiply(asset.getAverage_price(), asset.getLots() * asset.getAsset().getLot()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (!assets.isEmpty()) {
            assetDtos = assets.stream()
                    .map(pa -> mm.map(pa, AssetDto.class)).toList();
            var figi = assetDtos.stream().map(AssetDto::getFigi).toList();
            var lastPtices = assetService.getLastPrices(figi);
            for (var asset : assetDtos) {
                var lotQuanity = lotQuantityMap.get(asset.getFigi());
                var totalQuantity = lotQuanity * asset.getLots();
                var lastPrice = lastPtices.getOrDefault(asset.getFigi(), BigDecimal.ZERO);
                var volume = BigDecimalsUtils.multiply(asset.getAverage_price(), totalQuantity);
                var percentage = calculate((((initialPrice, currentPrice) -> BigDecimalsUtils.percentage(initialPrice, currentPrice).doubleValue())),
                        BigDecimalsUtils.subtract(lastPrice, asset.getAverage_price()), asset.getAverage_price());
                var profit = calculate((((initialPrice, currentPrice) -> BigDecimalsUtils.subtract(currentPrice, initialPrice).doubleValue())),
                        asset.getAverage_price(), lastPrice) * asset.getLots() * lotQuanity;
                var initialValue = BigDecimalsUtils.multiply(asset.getAverage_price(), totalQuantity);
                var currentValue = BigDecimalsUtils.multiply(lastPrice, totalQuantity);
                var portfolioShare = calculate((amount, pValue) -> BigDecimalsUtils.percentage(amount, pValue).doubleValue(), volume, portfolioValue);
                asset.setLastPrice(lastPrice);
                asset.setRoe(percentage);
                asset.setProfit(profit);
                asset.setPortfolioShare(portfolioShare);
                asset.setInitialValue(initialValue);
                asset.setCurrentValue(currentValue);
                portfolioProfit += profit;
            }
        }
        return PortfolioDto.builder()
                .portfolioName(portfolio.getPortfolioName())
                .userId(portfolio.getUserId())
                .id(portfolio.getId())
                .assets(assetDtos)
                .portfolioValue(portfolioValue)
                .portfolioProfit(portfolioProfit)
                .build();
    }

    private void validateSinglePortfolio(User user) {
        var portfolio = pr.findByUserId(user.getId());
        if (portfolio.isPresent()) throw new AlreadyExistsException("Portfolio for this user already exists");
    }

    private void validateBalance(List<PortfolioAsset> portfolioAssets, Balance balance, AssetDto assetDto) {
        if (assetDto.getType().equals(AssetOperationType.BUY)) {
            bs.checkSufficientBalance(balance, assetDto.getVolume());
        } else {
            pas.validateAssetInPortfolio(portfolioAssets, assetDto.getId(), assetDto.getLots());
        }
    }

    private Double calculate(CalculatorService calculatorService, BigDecimal value1, BigDecimal value2) {
        return calculatorService.calculate(value1, value2);
    }
}

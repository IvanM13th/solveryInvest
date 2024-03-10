package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.entity.PortfolioAsset;
import solveryinvest.stocks.enums.AssetOperationType;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.PortfolioAssetRepository;
import solveryinvest.stocks.service.PortfolioAssetService;
import solveryinvest.stocks.utils.BigDecimalsUtils;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortfolioAssetServiceImpl implements PortfolioAssetService {

    private final PortfolioAssetRepository par;

    private final BigDecimalsUtils bigDecimalsUtils;

    @Override
    @Transactional
    public void updateAssetsInPortfolio(List<PortfolioAsset> portfolioAssets, Long pfId, AssetDto assetDto) {
        var assetMap = portfolioAssets.stream().collect(Collectors.toMap(PortfolioAsset::getAssetId, Function.identity()));
        if (assetDto.getType().equals(AssetOperationType.BUY)) {
            updateWhenBuy(assetMap, pfId, assetDto);
        } else {
            updateWhenSell(assetMap, pfId, assetDto);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortfolioAsset> findByPortfolioId(Long portfolioId) {
        return par.findByPortfolioId(portfolioId);
    }

    @Override
    public void validateAssetInPortfolio(List<PortfolioAsset> portfolioAssets, Long assetId, Long lots) {
        var assetMap = portfolioAssets.stream().collect(Collectors.toMap(PortfolioAsset::getAssetId, Function.identity()));
        if (!assetMap.containsKey(assetId)) {
            throw new NotFoundException("В портфеле актив для продажи не найден");
        } else {
            var asset = assetMap.get(assetId);
            if (asset.getLots() < lots) throw new NotFoundException("В портфеле недостаточно активов для продажи");
        }
    }

    private void updateWhenBuy(Map<Long, PortfolioAsset> assetMap, Long pfId, AssetDto assetDto) {
        PortfolioAsset pa;
        if (!assetMap.containsKey(assetDto.getId())) {
            pa = PortfolioAsset.builder()
                    .portfolioId(pfId)
                    .assetId(assetDto.getId())
                    .lastUpdate(OffsetDateTime.now(Clock.systemUTC()))
                    .lots(assetDto.getLots())
                    .average_price(assetDto.getPurchasePrice())
                    .build();
        } else {
            pa = assetMap.get(assetDto.getId());
            var oldCount = pa.getLots();
            var averagePriceOld = pa.getAverage_price();
            var bought = assetDto.getLots();
            var assetAveragePrice = assetDto.getPurchasePrice();
            var numerator = BigDecimalsUtils.add(BigDecimalsUtils.multiply(averagePriceOld, oldCount), (BigDecimalsUtils.multiply(assetAveragePrice, bought)));
            var denominator = oldCount + bought;
            var updatedAverage = BigDecimalsUtils.division(numerator, BigDecimal.valueOf(denominator));
            pa.setLastUpdate(OffsetDateTime.now(Clock.systemUTC()));
            pa.setAverage_price(updatedAverage);
            pa.setLots(denominator);
        }
        par.save(pa);
    }

    private void updateWhenSell(Map<Long, PortfolioAsset> assetMap, Long pfId, AssetDto assetDto) {
        var pa = assetMap.get(assetDto.getId());
        pa.setLots(pa.getLots() - assetDto.getLots());
        if (pa.getLots() == 0) {
            par.delete(pa);
        } else par.save(pa);
    }
}

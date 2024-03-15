package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.entity.PortfolioAsset;

import java.util.List;

public interface PortfolioAssetService {

    void updateAssetsInPortfolio(List<PortfolioAsset> portfolioAssets, Long pfId, AssetDto assetDto);

    List<PortfolioAsset> findByPortfolioId(Long portfolioId);

    void validateAssetInPortfolio(List<PortfolioAsset> portfolioAssets, Long assetId, Long lots);
}

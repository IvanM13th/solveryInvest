package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.User;

public interface PortfolioService {
    PortfolioDto createPortfolio(User user, PortfolioDto portfolio);

    AssetDto updatePortfolio(Long portfolioId, AssetDto assetDto);

    PortfolioDto getByUserId(User user);
}

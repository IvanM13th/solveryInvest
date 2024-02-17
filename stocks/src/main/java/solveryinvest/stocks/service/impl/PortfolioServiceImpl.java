package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.Portfolio;
import solveryinvest.stocks.entity.PortfolioAssetHistory;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.AssetOperationType;
import solveryinvest.stocks.exception.AlreadyExistsException;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.PortfolioRepository;
import solveryinvest.stocks.service.BalanceService;
import solveryinvest.stocks.service.PortfolioAssetHistoryService;
import solveryinvest.stocks.service.PortfolioService;
import solveryinvest.stocks.utils.BigDecimalsUtils;

import java.time.OffsetDateTime;

@Service("portfolio-service")
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository pr;
    private final BalanceService bs;
    private final BigDecimalsUtils decimalsUtils;
    private final PortfolioAssetHistoryService pahs;
    private final ModelMapper mm;

    private static final Long ID = 11L;

    @Override
    @Transactional
    public PortfolioDto createPortfolio(User user, PortfolioDto portfolioDto) {
        validateSinglePortfolio(user);
        var portfolio = mm.map(portfolioDto, Portfolio.class);
        pr.save(portfolio);
        return mm.map(portfolio, PortfolioDto.class);
    }

    @Override
    @Transactional
    public AssetDto updatePortfolio(Long portfolioId, AssetDto assetDto) {
        var type = assetDto.getType();
        var balance = bs.getBalance(ID);
        if (type.equals(AssetOperationType.BUY)) {
            bs.checkSufficientBalance(balance, assetDto.getVolume());
        } else {

        }
        var portfolio = pr.findByUserId(ID)
                .orElseThrow(() -> new NotFoundException(String.format("Portfolio for user with id %s not found", ID)));
        pahs.save(PortfolioAssetHistory.builder()
                .portfolioId(portfolioId)
                .assetId(assetDto.getId())
                .dateTime(OffsetDateTime.now())
                .lots(assetDto.getLot())
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
        return null;
    }

    private void validateSinglePortfolio(User user) {
        var portfolio = pr.findByUserId(user.getId());
        if (portfolio.isPresent()) throw new AlreadyExistsException("Portfolio for this user already exists");
    }

    private void checkAssets(Long portfolioId) {

    }
}

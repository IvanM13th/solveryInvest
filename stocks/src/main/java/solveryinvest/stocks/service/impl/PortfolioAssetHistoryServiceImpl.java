package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.entity.PortfolioAssetHistory;
import solveryinvest.stocks.repository.PortfolioAssetHistoryRepository;
import solveryinvest.stocks.service.PortfolioAssetHistoryService;

@Service
@RequiredArgsConstructor
public class PortfolioAssetHistoryServiceImpl implements PortfolioAssetHistoryService {

    private final PortfolioAssetHistoryRepository pahr;

    @Override
    @Transactional
    public PortfolioAssetHistory save(PortfolioAssetHistory portfolioAsset) {
        return pahr.save(portfolioAsset);
    }
}

package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.PortfolioDto;
import solveryinvest.stocks.entity.Portfolio;
import solveryinvest.stocks.repository.PortfolioRepository;
import solveryinvest.stocks.service.BalanceService;
import solveryinvest.stocks.service.PortfolioService;

@Service("portfolio-service")
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository pr;
    private final BalanceService bs;
    private final ModelMapper mm;

    @Override
    @Transactional
    public PortfolioDto createPortfolio(PortfolioDto portfolioDto) {
        var id = 11;
        var portfolio = mm.map(portfolioDto, Portfolio.class);
        pr.save(portfolio);
        return mm.map(portfolio, PortfolioDto.class);
    }

}

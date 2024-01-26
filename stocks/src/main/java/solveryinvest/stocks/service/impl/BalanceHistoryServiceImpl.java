package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.entity.BalanceHistory;
import solveryinvest.stocks.repository.BalanceHistoryRepository;
import solveryinvest.stocks.service.BalanceHistoryService;

@Service("balance-history-service")
@RequiredArgsConstructor
public class BalanceHistoryServiceImpl implements BalanceHistoryService {
    private final BalanceHistoryRepository bhr;

    @Override
    @Transactional
    public BalanceHistory save(BalanceHistory history) {
        return bhr.save(history);
    }
}

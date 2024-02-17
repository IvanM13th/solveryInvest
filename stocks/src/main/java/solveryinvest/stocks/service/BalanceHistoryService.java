package solveryinvest.stocks.service;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.BalanceHistory;

public interface BalanceHistoryService {
    BalanceHistory save(BalanceHistory history);
}

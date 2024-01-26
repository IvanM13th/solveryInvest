package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.BalanceHistory;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {

}

package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solveryinvest.stocks.entity.BalanceHistory;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {

}

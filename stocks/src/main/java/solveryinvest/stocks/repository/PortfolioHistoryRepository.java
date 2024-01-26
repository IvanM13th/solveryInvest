package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.PortfolioHistory;

public interface PortfolioHistoryRepository extends JpaRepository<PortfolioHistory, Long> {
}

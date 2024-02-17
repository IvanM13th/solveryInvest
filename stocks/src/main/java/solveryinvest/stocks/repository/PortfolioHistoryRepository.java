package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solveryinvest.stocks.entity.PortfolioHistory;

@Repository
public interface PortfolioHistoryRepository extends JpaRepository<PortfolioHistory, Long> {
}

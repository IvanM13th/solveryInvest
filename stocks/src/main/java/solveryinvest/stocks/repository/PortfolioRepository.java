package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}

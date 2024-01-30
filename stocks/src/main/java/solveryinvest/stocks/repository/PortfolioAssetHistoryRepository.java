package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solveryinvest.stocks.entity.PortfolioAssetHistory;

@Repository
public interface PortfolioAssetHistoryRepository extends JpaRepository<PortfolioAssetHistory, Long> {

}

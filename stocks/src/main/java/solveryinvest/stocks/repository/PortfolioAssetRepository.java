package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.PortfolioAsset;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {

}

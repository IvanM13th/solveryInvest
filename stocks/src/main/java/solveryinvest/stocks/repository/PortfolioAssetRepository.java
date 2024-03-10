package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import solveryinvest.stocks.entity.PortfolioAsset;

import java.util.List;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {

    @Query("from PortfolioAsset ps join fetch Asset a on ps.assetId = a.id where ps.portfolioId = :portfolioId")
    List<PortfolioAsset> findByPortfolioId(@Param(value = "portfolioId") Long portfolioId);
}

package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import solveryinvest.stocks.entity.Asset;

public interface AssetsRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
}

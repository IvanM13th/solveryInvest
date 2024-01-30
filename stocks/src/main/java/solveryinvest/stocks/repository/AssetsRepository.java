package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import solveryinvest.stocks.entity.Asset;

@Repository
public interface AssetsRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
}

package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solveryinvest.stocks.entity.Asset;

public interface AssetsRepository extends JpaRepository<Asset, Long> {
}

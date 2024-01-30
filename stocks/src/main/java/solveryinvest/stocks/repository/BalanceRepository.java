package solveryinvest.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import solveryinvest.stocks.entity.Balance;

import java.util.Optional;
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Query("from Balance b where b.user_id=:id")
    Optional<Balance> findByUserId(@Param(value = "id") Long id);
}

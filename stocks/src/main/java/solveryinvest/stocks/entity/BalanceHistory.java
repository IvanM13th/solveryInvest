package solveryinvest.stocks.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solveryinvest.stocks.enums.OperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "balance_id")
    private Long balance_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_id", insertable = false, updatable = false)
    private Balance balance;
    private OffsetDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private BigDecimal amount;
}

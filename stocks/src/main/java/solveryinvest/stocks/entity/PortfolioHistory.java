package solveryinvest.stocks.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solveryinvest.stocks.enums.AssetOperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private OffsetDateTime dateTime;

    @Column(name = "portfolio_id")
    private Long portfolioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
    private Portfolio portfolio;

    @Column(name = "asset_id")
    private Long assetId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", insertable = false, updatable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private AssetOperationType operationType;

    private Long lots;

    private Long volume;

    private BigDecimal purchasePrice;
}

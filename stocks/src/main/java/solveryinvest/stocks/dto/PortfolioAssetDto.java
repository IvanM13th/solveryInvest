package solveryinvest.stocks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.entity.Portfolio;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioAssetDto {

    private Long id;

    private String name;

    private String ticker;

    private OffsetDateTime lastUpdate;

    private Long portfolioId;

    private Long assetId;

    private Long lots;

    private BigDecimal averagePrice;

    private Double roe;

    private Double profit;
}

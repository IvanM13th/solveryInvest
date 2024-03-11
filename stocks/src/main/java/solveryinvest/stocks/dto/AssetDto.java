package solveryinvest.stocks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solveryinvest.stocks.enums.AssetOperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDto {
    private Long id;
    private String figi;
    private String name;
    private String ticker;
    private Long lots;
    private BigDecimal purchasePrice;
    private BigDecimal averagePrice;
    private BigDecimal lastPrice;
    private OffsetDateTime dateTime;
    private BigDecimal volume;
    private AssetOperationType type;
    private BigDecimal initialValue;
    private BigDecimal currentValue;
    private Double roe;
    private Double profit;
    private Double portfolioShare;
}

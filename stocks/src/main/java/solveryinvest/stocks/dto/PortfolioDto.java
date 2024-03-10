package solveryinvest.stocks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioDto {
    private Long id;

    private Long userId;

    private String portfolioName;
    private Double portfolioRoe;
    private Double portfolioProfit;
    private BigDecimal portfolioValue;

    private List<AssetDto> assets;
}

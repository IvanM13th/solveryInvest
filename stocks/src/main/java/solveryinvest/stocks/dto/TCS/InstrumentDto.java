package solveryinvest.stocks.dto.TCS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solveryinvest.stocks.dto.AssetDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentDto {
    List<String> instrumentId;
    List<AssetDto> instruments;
}

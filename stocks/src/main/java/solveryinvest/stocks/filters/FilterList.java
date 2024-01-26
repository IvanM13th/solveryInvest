package solveryinvest.stocks.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterList {
    private FilterMergingType type = FilterMergingType.AND;
    private List<Filter> filters;
}

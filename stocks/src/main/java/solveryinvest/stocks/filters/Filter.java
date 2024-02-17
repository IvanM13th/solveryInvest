package solveryinvest.stocks.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {
    private String key;
    private QueryOperator type;
    private String value;
    private List<String> values;
    private Long valueStart;
    private Long valueEnd;
}


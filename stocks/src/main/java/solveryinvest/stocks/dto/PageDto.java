package solveryinvest.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private Long totalPages;
    private Long page;
    private Long totalElements;
    private Boolean hasNext;
    private List<T> content;
}

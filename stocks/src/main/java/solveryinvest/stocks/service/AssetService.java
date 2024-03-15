package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PageDto;
import solveryinvest.stocks.filters.FilterList;
import solveryinvest.stocks.filters.SortType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AssetService {

    void getAssetsWhenAppStarts();

    PageDto<AssetDto> findAllByFilters(List<FilterList> filters, Integer page, Integer pageSize, String field, SortType direction);

    Map<String, BigDecimal> getLastPrices(List<String> figi);
}

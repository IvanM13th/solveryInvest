package solveryinvest.stocks.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import solveryinvest.stocks.dto.AssetDto;
import solveryinvest.stocks.dto.PageDto;
import solveryinvest.stocks.dto.TCS.AssetsDto;
import solveryinvest.stocks.dto.TCS.InstrumentDto;
import solveryinvest.stocks.dto.TCS.LastPriceDto;
import solveryinvest.stocks.dto.TCS.LastPrices;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.filters.FilterList;
import solveryinvest.stocks.filters.SortFields;
import solveryinvest.stocks.filters.SortType;
import solveryinvest.stocks.repository.AssetsRepository;
import solveryinvest.stocks.service.AssetService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static solveryinvest.stocks.filters.AssetFilter.getSpecificationFromFilters;
import static solveryinvest.stocks.filters.FilterUtils.getSort;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final ObjectMapper mapper;

    private final AssetsRepository assetsRepository;

    private final RestTemplate restTemplate;

    private final ModelMapper modelMapper;

    @Value("${t.get.all}")
    private String url_getAll;

    @Value("${t.token}")
    private String token;

    @Value("${t.status.base}")
    private String statusBase;

    @Value("${t.get.last.price}")
    private String lastPriceUrl;

    private final Map<String, SortFields> sortFields = Map.of("name", SortFields.name,
            "id", SortFields.id);

    @Transactional
    public void getAssetsWhenAppStarts() {
        if (assetsRepository.findAll().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", String.format("Bearer %s", token));
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("instrumentStatus", statusBase);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url_getAll, request, String.class);
                if (Objects.nonNull(response.getBody())) {
                    var assets = mapper.readValue(response.getBody(), AssetsDto.class).getInstruments();
                    assetsRepository.saveAll(assets);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public PageDto<AssetDto> findAllByFilters(List<FilterList> filters, Integer page, Integer pageSize, String field, SortType direction) {
        final var pageable = getSort(page, pageSize, direction, () -> String.valueOf(sortFields.getOrDefault(field, SortFields.name)));
        if (Objects.isNull(filters)) filters = new ArrayList<>();
        final var spec = getSpecificationFromFilters(filters);
        final var assets = assetsRepository.findAll(spec, pageable);
        var priceMap = getLastPrices(assets.stream().map(Asset::getFigi).toList());
        List<AssetDto> assetsDto = new ArrayList<>();
        assets.forEach(asset -> {
            var dto = modelMapper.map(asset, AssetDto.class);
            dto.setLastPrice(priceMap.getOrDefault(dto.getFigi(), BigDecimal.ZERO));
            assetsDto.add(dto);
        });
        return PageDto.<AssetDto>builder()
                .totalPages((long) assets.getTotalPages())
                .totalElements(assets.getTotalElements())
                .hasNext(assets.hasNext())
                .content(assetsDto)
                .build();
    }

    @Override
    @Transactional
    public Map<String, BigDecimal> getLastPrices(List<String> figi) {
        var instr = InstrumentDto.builder().instrumentId(figi).build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", token));
        log.info("Authorisation is - {}", String.format("Bearer %s", token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstrumentDto> request = new HttpEntity<>(instr, headers);
        LastPrices response = restTemplate.postForObject(lastPriceUrl, request, LastPrices.class);
        Map<String, BigDecimal> priceMap = new HashMap<>();
        if (Objects.nonNull(response) && Objects.nonNull(response.getLastPrices())) {
            priceMap = response.getLastPrices().stream()
                    .collect(Collectors.toMap(LastPriceDto::getFigi, lastPriceDto -> lastPriceDto.getPrice().getUnits()));
        }
        return priceMap;
    }
}

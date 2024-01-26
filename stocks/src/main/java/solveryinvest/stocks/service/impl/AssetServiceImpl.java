package solveryinvest.stocks.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.repository.AssetsRepository;
import solveryinvest.stocks.service.AssetService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final ObjectMapper mapper;

    private final AssetsRepository assetsRepository;

    @Value("${t.get.all}")
    private String url_getAll;

    @Value("${t.token}")
    private String token;

    @Value("${t.status.base}")
    private String statusBase;

    @PostConstruct
    @Transactional
    void getAssets() {
        if (assetsRepository.findAll().isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", String.format("Bearer %s", token));
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("instrumentStatus", statusBase);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url_getAll, request, String.class);
                if (Objects.nonNull(response.getBody())) {
                    var assets = mapper.readValue(response.getBody(), Instruments.class).getInstruments();
                    assetsRepository.saveAll(assets);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Instruments {
        private List<Asset> instruments;
    }
}

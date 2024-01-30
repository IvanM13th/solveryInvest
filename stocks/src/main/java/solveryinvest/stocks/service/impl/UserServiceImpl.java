package solveryinvest.stocks.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.service.UserService;

import java.util.Collections;

@Service("user-service")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Value("${user.by.id}")
    private String userUrl;

    @Override
    public UserDto findById(User user) {
        var url = String.format("%s%s", userUrl, user.getId());
        var jwt = user.getJwt();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s",jwt));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDto.class);
        return response.getBody();
    }
}

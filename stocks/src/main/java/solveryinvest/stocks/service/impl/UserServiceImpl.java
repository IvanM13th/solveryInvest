package solveryinvest.stocks.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.service.UserService;

@Service("user-service")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Value("${user.by.id}")
    private String userUrl;

    @Override
    //TODO сюда надо будет добавить токен, когда прикручу секурити
    public UserDto findById(Long id) {
        var url = String.format("%s%s", userUrl, id);
        UserDto user = restTemplate.getForObject(url, UserDto.class);
        return user;
    }
}

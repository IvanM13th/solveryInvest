package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.UserDto;

public interface UserService {
    UserDto findById(Long id);
}

package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.entity.User;

public interface UserService {
    UserDto findById(User user);
}

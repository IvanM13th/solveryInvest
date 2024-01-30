package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.OperationType;

import java.math.BigDecimal;
import java.util.Optional;

public interface BalanceService {
    BalanceDto createBalance(User user);
    BalanceDto getBalanceDto(Long userId);
    Balance getBalance(Long userId);
    BalanceDto updateBalance(Long id, BigDecimal amount, OperationType type);

    void checkSufficientBalance(Balance balance, BigDecimal volume);
}

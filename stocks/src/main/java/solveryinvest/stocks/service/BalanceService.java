package solveryinvest.stocks.service;

import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.enums.OperationType;

import java.util.Optional;

public interface BalanceService {
    BalanceDto createBalance(Long userId);
    BalanceDto getBalance(Long userId);
    BalanceDto updateBalance(Long id, Double amount, OperationType type);
}

package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.entity.BalanceHistory;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.BalanceRepository;
import solveryinvest.stocks.service.BalanceHistoryService;
import solveryinvest.stocks.service.BalanceService;
import solveryinvest.stocks.service.UserService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service("balance-service")
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final UserService userService;

    private final BalanceHistoryService bhr;

    private final BalanceRepository br;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BalanceDto createBalance(Long userId) {
        var user = userService.findById(userId);
        validateUserBalance(userId);
        var balance = Balance.builder()
                .user_id(userId)
                .balance(new BigDecimal(0))
                .build();
        br.save(balance);
        return modelMapper.map(balance, BalanceDto.class);
    }

    @Override
    @Transactional
    public BalanceDto getBalance(Long userId) {
        var balance = br.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Balance for user with id %s not found", userId)));
        return modelMapper.map(balance, BalanceDto.class);
    }

    @Override
    @Transactional
    public BalanceDto updateBalance(Long id, Double amount, OperationType type) {
        var balance = br.findByUserId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Balance for user with id %s not found", id)));
        if (type.equals(OperationType.DEPOSIT)) {
            balance.setBalance(balance.getBalance().add(BigDecimal.valueOf(amount)));
        } else {
            balance.setBalance(balance.getBalance().subtract(BigDecimal.valueOf(amount)));
        }
        validateIfBalanceBelowZero(balance.getBalance());
        bhr.save(BalanceHistory.builder()
                .balance_id(balance.getId()).dateTime(OffsetDateTime.now()).amount(BigDecimal.valueOf(amount)).operationType(type)
                .build());
        return modelMapper.map(balance, BalanceDto.class);
    }

    private void validateUserBalance(Long userId) {
        var balance = br.findByUserId(userId);
        if (balance.isPresent())
            throw new NotFoundException(String.format("Balance for user with id %s already exists", userId));
    }

    private void validateIfBalanceBelowZero(BigDecimal balance) {
        var value = balance.compareTo(BigDecimal.ZERO);
        if (value < 0) throw new NotFoundException("Not enough money");
    }
}

package solveryinvest.stocks.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solveryinvest.stocks.dto.BalanceDto;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.entity.BalanceHistory;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.exception.AlreadyExistsException;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.BalanceRepository;
import solveryinvest.stocks.service.BalanceHistoryService;
import solveryinvest.stocks.service.BalanceService;
import solveryinvest.stocks.service.UserService;
import solveryinvest.stocks.utils.BigDecimalsUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service("balance-service")
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final UserService userService;

    private final BalanceHistoryService bhr;

    private final BalanceRepository br;

    private final BigDecimalsUtils decimals;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BalanceDto createBalance(User user) {
        var userDto = userService.findById(user);
        checkIfBalanceExists(user.getId());
        var balance = Balance.builder()
                .user_id(user.getId())
                .balance(new BigDecimal(0))
                .build();
        br.save(balance);
        return modelMapper.map(balance, BalanceDto.class);
    }

    @Override
    @Transactional
    public BalanceDto getBalanceDto(Long userId) {
        var balance = br.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Balance for user with id %s not found", userId)));
        return modelMapper.map(balance, BalanceDto.class);
    }

    @Override
    @Transactional
    public Balance getBalance(Long userId) {
        return br.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Balance for user with id %s not found", userId)));
    }

    @Override
    @Transactional
    public BalanceDto updateBalance(Long id, BigDecimal amount, OperationType type) {
        var balance = getBalance(id);
        var newValue = getNewBalanceValue(balance.getBalance(), amount, type);
        balance.setBalance(newValue);
        decimals.validateBalance(balance.getBalance());
        bhr.save(BalanceHistory.builder()
                .balance_id(balance.getId()).dateTime(OffsetDateTime.now()).amount(amount).operationType(type)
                .build());
        return modelMapper.map(balance, BalanceDto.class);
    }

    @Override
    public void checkSufficientBalance(Balance balance, BigDecimal volume) {
        var result = decimals.subtract(balance.getBalance(), volume);
        decimals.validateBalance(result);
    }

    private void checkIfBalanceExists(Long userId) {
        var balance = br.findByUserId(userId);
        if (balance.isPresent())
            throw new AlreadyExistsException(String.format("Balance for user with id %s already exists", userId));
    }

    private BigDecimal getNewBalanceValue(BigDecimal value1, BigDecimal value2, OperationType operationType) {
        if (operationType.equals(OperationType.DEPOSIT)) {
            return decimals.add(value1, value2);
        } else {
            return decimals.subtract(value1, value2);
        }
    }
}

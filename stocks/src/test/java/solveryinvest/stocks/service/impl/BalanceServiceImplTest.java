package solveryinvest.stocks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import solveryinvest.stocks.dto.UserDto;
import solveryinvest.stocks.entity.Balance;
import solveryinvest.stocks.entity.User;
import solveryinvest.stocks.enums.OperationType;
import solveryinvest.stocks.exception.AlreadyExistsException;
import solveryinvest.stocks.exception.NotEnoughMoneyException;
import solveryinvest.stocks.exception.NotFoundException;
import solveryinvest.stocks.repository.BalanceRepository;
import solveryinvest.stocks.service.BalanceHistoryService;
import solveryinvest.stocks.service.BalanceService;
import solveryinvest.stocks.service.UserService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class BalanceServiceImplTest {

    private BalanceService balanceService;

    @Mock
    private BalanceHistoryService bhr;

    @Mock
    private BalanceRepository br;
    @Spy
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    private static final User user = User.builder().id(1L).email("123@321.ru").build();
    private static final UserDto userDto = UserDto.builder().id(1L).email("123@321.ru").build();

    private static final Balance newBalance = Balance.builder()
            .userId(user.getId())
            .balance(new BigDecimal(0))
            .build();

    private static final Balance newSavedBalance = Balance.builder()
            .userId(1L)
            .id(1L)
            .balance(new BigDecimal(0))
            .build();

    @BeforeEach
    void setUp() {
        balanceService = new BalanceServiceImpl(userService, bhr, br, modelMapper);
    }

    @Test
    void whenUserFoundThenCreateBalance() {
        when(userService.findById(user)).thenReturn(userDto);
        when(br.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(br.save(newBalance)).thenReturn(newSavedBalance);
        var result = balanceService.createBalance(user);
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, balanceService.createBalance(user).getBalance());
    }

    @Test
    void whenBalanceExistsThenThrowException() {
        when(userService.findById(user)).thenReturn(userDto);
        when(br.findByUserId(user.getId())).thenReturn(Optional.of(new Balance()));
        assertThrows(AlreadyExistsException.class, () -> balanceService.createBalance(user));
    }

    @Test
    void whenGetBalanceThenReturnDto() {
        when(br.findByUserId(user.getId())).thenReturn(Optional.of(new Balance()));
        var result = balanceService.getBalanceDto(user.getId());
        assertNotNull(result);
    }

    @Test
    void whenBalanceNotFoundThenThrowException() {
        when(br.findByUserId(user.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> balanceService.getBalance(user.getId()));
    }

    @Test
    void updateBalanceWhenDepositThenBalanceIncreased() {
        var balance = Balance.builder().id(1L).balance(BigDecimal.valueOf(0.00)).build();
        var amount = BigDecimal.valueOf(5000.0);
        var type = OperationType.DEPOSIT;
        when(br.findByUserId(user.getId()))
                .thenReturn(Optional.of(balance));
        var result = balanceService.updateBalance(1L, amount, type);
        assertEquals(amount, result.getBalance());
    }

    @Test
    void updateBalanceWhenWithdrawAndNotEnoughMoneyThenThrowException() {
        var balance = Balance.builder().id(1L).balance(BigDecimal.valueOf(3000.0)).build();
        var amount = BigDecimal.valueOf(5000.0);
        var type = OperationType.WITHDRAW;
        when(br.findByUserId(user.getId()))
                .thenReturn(Optional.of(balance));
        assertThrows(NotEnoughMoneyException.class, () -> balanceService.updateBalance(1L, amount, type));
    }
}
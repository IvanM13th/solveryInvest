package solveryinvest.stocks.utils;

import org.springframework.stereotype.Component;
import solveryinvest.stocks.exception.NotEnoughMoneyException;
import solveryinvest.stocks.exception.NotFoundException;

import java.math.BigDecimal;

@Component("big-decimals-utils")
public class BigDecimalsUtils {

    public BigDecimal add(BigDecimal value1, BigDecimal value2) {
        return value1.add(value2);
    }

    public BigDecimal subtract(BigDecimal from, BigDecimal value) {
        return from.subtract(value);
    }

    public void validateBalance(BigDecimal balance) {
        var value = balance.compareTo(BigDecimal.ZERO);
        if (value < 0) throw new NotEnoughMoneyException("Not enough money");
    }
}

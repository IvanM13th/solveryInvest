package solveryinvest.stocks.utils;

import org.springframework.stereotype.Component;
import solveryinvest.stocks.exception.NotEnoughMoneyException;
import solveryinvest.stocks.exception.NotFoundException;

import java.math.BigDecimal;
import java.math.MathContext;

@Component("big-decimals-utils")
public class BigDecimalsUtils {

    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        return value1.add(value2);
    }

    public static BigDecimal subtract(BigDecimal from, BigDecimal value) {
        return from.subtract(value);
    }

    public static BigDecimal multiply(BigDecimal value, Long multiplier) {
        return value.multiply(BigDecimal.valueOf(multiplier));
    }

    public static BigDecimal division(BigDecimal value, BigDecimal divisor) {
        MathContext mc = new MathContext(5);
        return value.divide(divisor, mc);
    }

    public static BigDecimal percentage(BigDecimal value, BigDecimal divisor) {
        return value.divide(divisor, new MathContext(5)).multiply(new BigDecimal(100));
    }

    public static void validateBalance(BigDecimal balance) {
        var value = balance.compareTo(BigDecimal.ZERO);
        if (value < 0) {
            throw new NotEnoughMoneyException("Not enough money");
        }
    }
}

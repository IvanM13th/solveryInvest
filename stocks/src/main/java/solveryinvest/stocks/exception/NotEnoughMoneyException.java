package solveryinvest.stocks.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}

package solveryinvest.stocks.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}

package solveryinvest.stocks.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import solveryinvest.stocks.dto.ExceptionResponseDto;

import java.net.ConnectException;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({NotFoundException.class})
    public @ResponseBody ExceptionResponseDto handleNotFoundException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ExceptionResponseDto.builder()
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .date(new Date(System.currentTimeMillis()))
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public @ResponseBody ExceptionResponseDto handleAlreadyExistsException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ExceptionResponseDto.builder()
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .date(new Date(System.currentTimeMillis()))
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public @ResponseBody ExceptionResponseDto handleHttpClientErrorException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ExceptionResponseDto.builder()
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .date(new Date(System.currentTimeMillis()))
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({ConnectException.class})
    public @ResponseBody ExceptionResponseDto handleConnectException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ExceptionResponseDto.builder()
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .date(new Date(System.currentTimeMillis()))
                .message(String.format("Service is unavailable \n %s",e.getMessage()))
                .build();
    }

    @ExceptionHandler({NotEnoughMoneyException.class})
    public @ResponseBody ExceptionResponseDto handleNotEnoughMoneyException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ExceptionResponseDto.builder()
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .date(new Date(System.currentTimeMillis()))
                .message(e.getMessage())
                .build();
    }
}

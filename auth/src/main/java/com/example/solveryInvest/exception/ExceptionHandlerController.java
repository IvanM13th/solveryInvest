package com.example.solveryInvest.exception;

import com.example.solveryInvest.dto.ExceptionResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

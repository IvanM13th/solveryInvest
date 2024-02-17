package com.example.solveryInvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDto {
    private Integer code;
    private Date date;
    private String message;
}

package com.example.solveryInvest.dto;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HazelcastUserData implements Serializable {
    private Long id;
    private HttpSession session;
}

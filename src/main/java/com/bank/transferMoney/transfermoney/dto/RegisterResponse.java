package com.bank.transferMoney.transfermoney.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterResponse {
    private String message;
    private String fullName;
    private LocalDateTime createdAt;
}

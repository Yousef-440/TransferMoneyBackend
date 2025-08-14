package com.bank.transferMoney.transfermoney.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginBankResponse {
    private String firstName;
    private String message;
    private String token;
    private BigDecimal balance;
    private String accountNumber;
}

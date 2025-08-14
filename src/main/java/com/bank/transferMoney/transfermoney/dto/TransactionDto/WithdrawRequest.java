package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawRequest {
    private String accountNumber;
    @Min(value = 5, message = "The amount must be greater than 5")
    private BigDecimal amount;
    private String pinCode;
    private String description;
}

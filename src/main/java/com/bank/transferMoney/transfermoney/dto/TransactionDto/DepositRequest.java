package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    private String accountNumber;
    private Double amount;
}

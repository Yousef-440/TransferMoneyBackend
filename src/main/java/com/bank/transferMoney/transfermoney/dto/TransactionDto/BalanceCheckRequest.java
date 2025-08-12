package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceCheckRequest {
    private String accountNumber;
}

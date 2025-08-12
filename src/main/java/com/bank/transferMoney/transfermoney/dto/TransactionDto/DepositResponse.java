package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.bank.transferMoney.transfermoney.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {
    private String accountNumber;
    private String accountName;
    private Double depositedAmount;
    private Double newBalance;
    private TransactionType transactionType;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
}

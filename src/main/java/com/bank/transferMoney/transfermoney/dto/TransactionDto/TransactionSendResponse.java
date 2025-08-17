package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.bank.transferMoney.transfermoney.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionSendResponse {
    private int id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;
}

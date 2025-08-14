package com.bank.transferMoney.transfermoney.dto.TransactionDto;

import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawResponse {
    private String accountNumber;
    private BigDecimal withdrawnAmount;
    private BigDecimal balanceAfter;
    private TransactionStatus status;
    private String message;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm")
    private LocalDateTime timestamp;
}

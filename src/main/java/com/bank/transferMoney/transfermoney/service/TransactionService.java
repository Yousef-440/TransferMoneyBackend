package com.bank.transferMoney.transfermoney.service;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.BalanceCheckRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositResponse;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<ApiResponseDto<DepositResponse>> depositMoney(DepositRequest depositRequest);

    ResponseEntity<ApiResponseDto<String>> checkBalance(String accountNumber);
}

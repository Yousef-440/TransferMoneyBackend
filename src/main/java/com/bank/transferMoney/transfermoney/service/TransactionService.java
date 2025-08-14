package com.bank.transferMoney.transfermoney.service;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.*;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<ApiResponseDto<DepositResponse>> depositMoney(DepositRequest depositRequest);

    ResponseEntity<ApiResponseDto<String>> checkBalance(String accountNumber);

    ResponseEntity<ApiResponseDto<?>> withdrawMoney(WithdrawRequest withdrawRequest);
}

package com.bank.transferMoney.transfermoney.service;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.*;
import com.bank.transferMoney.transfermoney.entity.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<ApiResponseDto<DepositResponse>> depositMoney(DepositRequest depositRequest);

    ResponseEntity<ApiResponseDto<String>> checkBalance(String accountNumber);

    ResponseEntity<ApiResponseDto<?>> withdrawMoney(WithdrawRequest withdrawRequest);

    ResponseEntity<ApiResponseDto<List<TransactionSendResponse>>> getTransactions(String account);
}

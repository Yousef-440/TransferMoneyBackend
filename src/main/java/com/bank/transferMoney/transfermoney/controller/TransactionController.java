package com.bank.transferMoney.transfermoney.controller;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.BalanceCheckRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositResponse;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import com.bank.transferMoney.transfermoney.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final UserRepository userRepository;
    private final TransactionService transactionService;


    @PostMapping(path = "/deposit")
    public ResponseEntity<ApiResponseDto<DepositResponse>>depositMoney(@RequestBody DepositRequest depositRequest){
        return transactionService.depositMoney(depositRequest);
    }

    @GetMapping(path = "/balance")
    public ResponseEntity<ApiResponseDto<String>> balance(@RequestParam("account") String account) {
        log.info("balance endpoint started");
        return transactionService.checkBalance(account);
    }
}

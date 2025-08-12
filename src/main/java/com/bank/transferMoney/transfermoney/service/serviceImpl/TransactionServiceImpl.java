package com.bank.transferMoney.transfermoney.service.serviceImpl;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.BalanceCheckRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositRequest;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.DepositResponse;
import com.bank.transferMoney.transfermoney.entity.Transaction;
import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.bank.transferMoney.transfermoney.enumeration.TransactionType;
import com.bank.transferMoney.transfermoney.exceptoin.HandleException;
import com.bank.transferMoney.transfermoney.repository.TransactionRepository;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import com.bank.transferMoney.transfermoney.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<DepositResponse>> depositMoney(DepositRequest depositRequest) {
        String accountNumber = depositRequest.getAccountNumber();
        Double money = depositRequest.getAmount();

        if (money == null || money <= 0) {
            throw new HandleException("Please enter an amount of money");
        }
        User user = userRepository.findByAccountNumber(accountNumber).orElseThrow(
                ()->new UsernameNotFoundException("Account does not exist")
        );
        user.setAccountBalance(user.getAccountBalance() + money);
        userRepository.save(user);

        Transaction transaction = Transaction.builder()
                .receiver(user)
                .amount(money)
                .transactionType(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .build();
        transactionRepository.save(transaction);

        DepositResponse depositResponse = DepositResponse.builder()
                .accountNumber(accountNumber)
                .accountName(user.getFirstName() + " " + user.getLastName())
                .depositedAmount(money)
                .newBalance(user.getAccountBalance())
                .transactionType(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .transactionDate(transaction.getTransactionDate())
                .build();
        ApiResponseDto<DepositResponse> responseDto = ApiResponseDto.<DepositResponse>builder()
                .status("success")
                .message("Deposit Successfully")
                .data(depositResponse)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponseDto<String>> checkBalance(String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()->new HandleException("User not Found"));

        String balance = String.valueOf(user.getAccountBalance());

        ApiResponseDto<String> responseDto = ApiResponseDto.<String>builder()
                .status("success")
                .message("The Balance is")
                .data(balance)
                .build();
        return ResponseEntity.ok(responseDto);

    }
}

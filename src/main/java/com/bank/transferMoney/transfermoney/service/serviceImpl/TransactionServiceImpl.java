package com.bank.transferMoney.transfermoney.service.serviceImpl;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.TransactionDto.*;
import com.bank.transferMoney.transfermoney.entity.Transaction;
import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.bank.transferMoney.transfermoney.enumeration.TransactionType;
import com.bank.transferMoney.transfermoney.exceptoin.HandleException;
import com.bank.transferMoney.transfermoney.repository.TransactionRepository;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import com.bank.transferMoney.transfermoney.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<DepositResponse>> depositMoney(DepositRequest depositRequest) {
        String accountNumber = depositRequest.getAccountNumber();
        BigDecimal money = depositRequest.getAmount();

        if (money == null || money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new HandleException("Please enter an amount of money greater than 0");
        }

        User user = userRepository.findByAccountNumber(accountNumber).orElseThrow(
                ()->new HandleException("Account does not exist")
        );
        user.setAccountBalance(user.getAccountBalance().add(money));
        userRepository.save(user);

        Transaction transaction = Transaction.builder()
                .receiver(user)
                .amount(money)
                .transactionType(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .description("none")
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

    @Override
    public ResponseEntity<ApiResponseDto<?>> withdrawMoney(WithdrawRequest withdrawRequest) {

        log.info("Attempting withdrawal for account: {}", withdrawRequest.getAccountNumber());

        User user = userRepository.findByAccountNumber(withdrawRequest.getAccountNumber()).orElseThrow(
                () -> {
                    log.warn("User not found with account number: {}", withdrawRequest.getAccountNumber());
                    return new HandleException("There is no user in the account number");
                }
        );

        BigDecimal amount = withdrawRequest.getAmount();
        BigDecimal totalAmount = user.getAccountBalance();

        log.debug("Current balance: {}, Requested amount: {}", totalAmount, amount);

        if(amount.compareTo(totalAmount) > 0){
            log.warn("Insufficient balance for account: {}, Requested: {}, Available: {}",
                    withdrawRequest.getAccountNumber(), amount, totalAmount);

            ApiResponseDto<?> responseDto = ApiResponseDto.builder()
                    .status("Fail")
                    .message("The balance is not sufficient to complete the withdrawal process")
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        user.setAccountBalance(totalAmount.subtract(amount));
        userRepository.save(user);

        log.info("Withdrawal successful for account: {}, Amount withdrawn: {}, Remaining balance: {}",
                withdrawRequest.getAccountNumber(), amount, user.getAccountBalance());

        Transaction transaction = Transaction.builder()
                .receiver(user)
                .amount(amount)
                .transactionType(TransactionType.WITHDRAW)
                .status(TransactionStatus.COMPLETED)
                .description(withdrawRequest.getDescription())
                .build();
        transactionRepository.save(transaction);


        String message = amount + " was withdrawn, remaining amount is: " + user.getAccountBalance();
        WithdrawResponse withdrawResponse = WithdrawResponse.builder()
                .accountNumber(withdrawRequest.getAccountNumber())
                .withdrawnAmount(amount)
                .balanceAfter(user.getAccountBalance())
                .status(TransactionStatus.COMPLETED)
                .message(message)
                .build();

        ApiResponseDto<WithdrawResponse> responseDto = ApiResponseDto.<WithdrawResponse>builder()
                .status("success")
                .message("withdrawn successfully")
                .data(withdrawResponse)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<List<TransactionSendResponse>>> getTransactions(String account) {
        log.info("getTransaction Method isStarted");
        User user = userRepository.findByAccountNumber(account)
                .orElseThrow(()->new HandleException("User not Found"));

        List<Transaction> transactionList = transactionRepository.findByReceiver(user);

        List<TransactionSendResponse> transactionSendResponse = transactionList.stream()
                .map(transaction -> TransactionSendResponse.builder()
                        .id(user.getId())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType())
                        .transactionStatus(transaction.getStatus())
                        .transactionDate(transaction.getTransactionDate())
                        .build())
                .toList();

        ApiResponseDto<List<TransactionSendResponse>> responseDto = ApiResponseDto.<List<TransactionSendResponse>>builder()
                .status("success")
                .message("Transaction Send Successfully")
                .data(transactionSendResponse)
                .build();

        return ResponseEntity.ok(responseDto);
    }

}

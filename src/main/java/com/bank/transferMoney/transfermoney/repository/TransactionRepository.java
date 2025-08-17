package com.bank.transferMoney.transfermoney.repository;

import com.bank.transferMoney.transfermoney.entity.Transaction;
import com.bank.transferMoney.transfermoney.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByReceiver(User receiver);
}

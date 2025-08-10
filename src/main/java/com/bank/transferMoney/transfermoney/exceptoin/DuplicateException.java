package com.bank.transferMoney.transfermoney.exceptoin;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
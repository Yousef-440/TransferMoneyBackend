package com.bank.transferMoney.transfermoney.utils;

import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        int number1 = 120582;
        int number2 = 524635;

        int randomNumber = (int) Math.floor(Math.random() * (number2 - number1 + 1) + number1);
        String year = String.valueOf(Year.now().getValue());
        String randomNum = String.valueOf(randomNumber);

        return year + randomNum;
    }
}

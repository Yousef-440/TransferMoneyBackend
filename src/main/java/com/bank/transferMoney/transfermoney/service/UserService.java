package com.bank.transferMoney.transfermoney.service;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.RegisterDto;
import com.bank.transferMoney.transfermoney.dto.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto<RegisterResponse>> signup(RegisterDto registerDto);
}

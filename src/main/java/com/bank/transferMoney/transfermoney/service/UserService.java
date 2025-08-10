package com.bank.transferMoney.transfermoney.service;

import com.bank.transferMoney.transfermoney.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto<RegisterResponse>> signup(RegisterDto registerDto);

    ResponseEntity<ApiResponseDto<LoginResponse>> login(LoginRequest loginRequest);
}

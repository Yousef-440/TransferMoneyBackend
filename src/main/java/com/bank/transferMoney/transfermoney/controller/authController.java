package com.bank.transferMoney.transfermoney.controller;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.RegisterDto;
import com.bank.transferMoney.transfermoney.dto.RegisterResponse;
import com.bank.transferMoney.transfermoney.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v1/user")
public class authController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<RegisterResponse>> register(@Valid @RequestBody RegisterDto registerDto){
        return userService.signup(registerDto);
    }
}

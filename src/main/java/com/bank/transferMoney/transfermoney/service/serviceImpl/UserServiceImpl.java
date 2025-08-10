package com.bank.transferMoney.transfermoney.service.serviceImpl;

import com.bank.transferMoney.transfermoney.dto.ApiResponseDto;
import com.bank.transferMoney.transfermoney.dto.RegisterDto;
import com.bank.transferMoney.transfermoney.dto.RegisterResponse;
import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.exceptoin.DuplicateException;
import com.bank.transferMoney.transfermoney.mapper.UserMapper;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import com.bank.transferMoney.transfermoney.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<ApiResponseDto<RegisterResponse>> signup(RegisterDto registerDto) {
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());

        if (existingUser.isPresent()) {
            log.warn("Signup failed: Email already exists - {}", registerDto.getEmail());
            throw new DuplicateException("Sorry, Email already exists");
        }

        User user = userMapper.toEntity(registerDto);
        user.setAccountBalance(2500.00);
        user.setAccountNumber("123456789");
        user.setStatus("true");

        userRepository.save(user);


        RegisterResponse response = RegisterResponse.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .createdAt(user.getCreatedAt())
                .message("Welcome To Yousef-Bank")
                .build();

        ApiResponseDto<RegisterResponse> apiResponseDto = ApiResponseDto.<RegisterResponse>builder()
                .status("success")
                .message("Welcome To Bank" + user.getFirstName())
                .data(response)
                .build();

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}

package com.bank.transferMoney.transfermoney.service.serviceImpl;

import com.bank.transferMoney.transfermoney.dto.*;
import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.enumeration.Role;
import com.bank.transferMoney.transfermoney.enumeration.Status;
import com.bank.transferMoney.transfermoney.exceptoin.DuplicateException;
import com.bank.transferMoney.transfermoney.exceptoin.HandleException;
import com.bank.transferMoney.transfermoney.mapper.UserMapper;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import com.bank.transferMoney.transfermoney.security.CustomUserDetails;
import com.bank.transferMoney.transfermoney.security.JwtGenerator;
import com.bank.transferMoney.transfermoney.service.UserService;
import com.bank.transferMoney.transfermoney.utils.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public ResponseEntity<ApiResponseDto<RegisterResponse>> signup(RegisterDto registerDto) {
        log.info("Signup request received for email: {}", registerDto.getEmail());

        userRepository.findByEmail(registerDto.getEmail()).ifPresent(user -> {
            log.warn("Signup failed: Email already exists - {}", registerDto.getEmail());
            throw new DuplicateException("Sorry, Email already exists");
        });
        log.debug("No existing user found with email: {}", registerDto.getEmail());


        User user = userMapper.toEntity(registerDto);
        log.debug("Mapped RegisterDto to User entity: {}", user);


        user.setAccountBalance(2500.00);
        user.setAccountNumber(accountNumberGenerator.generateAccountNumber());
        user.setStatus(Status.ACTIVE);
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        userRepository.save(user);

        log.info("User saved successfully: {}", user.getEmail());

        RegisterResponse response = RegisterResponse.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .createdAt(user.getCreatedAt())
                .message("Welcome To CS-Bank")
                .build();

        ApiResponseDto<RegisterResponse> apiResponseDto = ApiResponseDto.<RegisterResponse>builder()
                .status("success")
                .message("Welcome To Bank, " + user.getFirstName())
                .data(response)
                .build();

        log.info("Signup process completed successfully for email: {}", user.getEmail());

        return ResponseEntity.ok(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<LoginResponse>> login(LoginRequest loginRequest) {
        log.info("Login Method is Started");
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authToken =
                    UsernamePasswordAuthenticationToken.unauthenticated(email, password);

            authentication = authenticationManager.authenticate(authToken);

            log.info("Authentication successful for email: {}", email);

        } catch (BadCredentialsException e) {
            log.warn("Wrong password for email: {}", email);
            throw new HandleException("Incorrect Password");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwtToken = jwtGenerator.generateToken(userDetails, userDetails.getRole().name());
        log.info("JWT generated for user: {} with role: {}", userDetails.getUsername(), userDetails.getRole().name());

        String welcomeMessage = "Welcome Back, " + userDetails.getName().toUpperCase();
        String firstName = userDetails.getName();
        LoginResponse loginResponse = LoginResponse.builder()
                .firstName(firstName)
                .message(welcomeMessage)
                .token(jwtToken)
                .balance(userDetails.getCurrentBalance())
                .accountNumber(userDetails.getAccountNumber())
                .build();

        ApiResponseDto<LoginResponse> response = ApiResponseDto.<LoginResponse>builder()
                .status("success")
                .message("Login successful!")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}

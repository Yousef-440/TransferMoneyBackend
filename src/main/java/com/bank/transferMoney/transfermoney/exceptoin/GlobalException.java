package com.bank.transferMoney.transfermoney.exceptoin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(HandleException.class)
    public ResponseEntity<?> handleNotFoundException(HandleException ex){
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateException ex){
        log.info("Duplicate started exception");
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("time", LocalDateTime.now());
        error.put("status", HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, List<String>>>> handleValidationException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, List<String>> errorsByField = new LinkedHashMap<>();

        for (var fieldError : fieldErrors) {
            errorsByField.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                    .add(fieldError.getDefaultMessage());
        }

        Map<String, Map<String, List<String>>> response = Map.of("errors", errorsByField);

        return ResponseEntity.badRequest().body(response);
    }

}

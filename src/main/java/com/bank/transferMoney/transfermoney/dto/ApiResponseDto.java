package com.bank.transferMoney.transfermoney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponseDto <T> {
    private String status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}

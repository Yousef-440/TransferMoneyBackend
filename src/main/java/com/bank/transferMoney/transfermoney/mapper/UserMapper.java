package com.bank.transferMoney.transfermoney.mapper;

import com.bank.transferMoney.transfermoney.dto.RegisterDto;
import com.bank.transferMoney.transfermoney.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (RegisterDto registerDto);
}


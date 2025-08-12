package com.bank.transferMoney.transfermoney.security;

import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public String getName() {
        return user.getFirstName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Double getCurrentBalance(){
        return user.getAccountBalance();
    }

    public String getAccountNumber(){
        return user.getAccountNumber();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

}

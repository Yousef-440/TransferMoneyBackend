package com.bank.transferMoney.transfermoney.security;


import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class DetailsUser implements UserDetails {
    private final User user;
    public DetailsUser(User user){
        this.user = user;
    }

    public String getName() {
        return user.getFirstName();
    }

    public String getEmail() {
        return user.getEmail();
    }
    public BigDecimal getBalance(){
        return user.getAccountBalance();
    }
    public String getAccountNumber(){
        return user.getAccountNumber();
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

    @Override
    public String getUsername() {
        return user.getFirstName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

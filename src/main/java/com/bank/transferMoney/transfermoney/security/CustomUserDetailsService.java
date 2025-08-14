package com.bank.transferMoney.transfermoney.security;

import com.bank.transferMoney.transfermoney.entity.User;
import com.bank.transferMoney.transfermoney.exceptoin.HandleException;
import com.bank.transferMoney.transfermoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new HandleException("User not Found"));
        return new DetailsUser(user);
    }
}

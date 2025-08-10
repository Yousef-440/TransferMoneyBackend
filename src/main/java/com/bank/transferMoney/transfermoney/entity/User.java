package com.bank.transferMoney.transfermoney.entity;

import com.bank.transferMoney.transfermoney.enumeration.Gender;
import com.bank.transferMoney.transfermoney.enumeration.Role;
import com.bank.transferMoney.transfermoney.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user", schema = "bank")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String accountNumber;
    private Double accountBalance;
    private String phoneNumber;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm")
    private LocalDateTime modifiedAt;
}

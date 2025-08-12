package com.bank.transferMoney.transfermoney.entity;

import com.bank.transferMoney.transfermoney.enumeration.TransactionType;
import com.bank.transferMoney.transfermoney.enumeration.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "transaction", schema = "bank")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm")
    @CreationTimestamp
    private LocalDateTime transactionDate;

    private String description;
}

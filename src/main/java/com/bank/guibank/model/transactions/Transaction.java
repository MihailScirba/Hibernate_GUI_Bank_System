package com.bank.guibank.model.transactions;

import com.bank.guibank.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double amount;

    @Column(name = "transaction_date")
    private LocalDateTime date;

    @Column
    private String type;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Transaction() {
    }

    public Transaction(double amount, LocalDateTime date, String type,
                       User user) {
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.user = user;
    }
}

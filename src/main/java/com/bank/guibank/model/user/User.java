package com.bank.guibank.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(unique = true)
    private String idnp;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private LocalDate birthday;

    @Column
    private double balance;

    @Column(name = "has_account")
    private boolean account;

    @OneToOne(mappedBy = "user")
    private UserAccount userAccount;

    public User() {

    }

    public User(String idnp, String firstname, String lastname,
                LocalDate birthday, double balance, boolean account) {
        this.idnp = idnp;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.balance = balance;
        this.account = account;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }
}

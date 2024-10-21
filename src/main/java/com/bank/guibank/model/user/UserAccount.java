package com.bank.guibank.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column
    private String username;

    @Column(name = "user_password")
    private String password;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserAccount() {
    }

    public UserAccount(String username, String password, User user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }
}

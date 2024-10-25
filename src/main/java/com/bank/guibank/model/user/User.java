package com.bank.guibank.model.user;

import com.bank.guibank.model.database.transaction.TransactionDAO;
import com.bank.guibank.model.database.user.UserAccountDAO;
import com.bank.guibank.model.interfaces.UserAccountOperationsInterface;
import com.bank.guibank.model.interfaces.UserTransactionsInterface;
import com.bank.guibank.model.transactions.Transaction;
import com.bank.guibank.model.user.exceptions.NotEnoughMoneyException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserTransactionsInterface,
        UserAccountOperationsInterface {
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

    public boolean deposit(double amount) {
        this.balance += amount;
        var transaction = new Transaction(amount, LocalDateTime.now(),
                "deposit", this);
        TransactionDAO dao = new TransactionDAO();
        return dao.makeTransaction(transaction);
    }

    public boolean withdraw(double amount) throws NotEnoughMoneyException {
        if (this.balance < amount) {
            throw new NotEnoughMoneyException();
        }
        this.balance -= amount;
        var transaction = new Transaction(amount, LocalDateTime.now(),
                "deposit", this);
        TransactionDAO dao = new TransactionDAO();
        return dao.makeTransaction(transaction);
    }

    @Override
    public boolean transfer(double amount, @NotNull User recipient)
            throws NotEnoughMoneyException {
        if (this.balance < amount) {
            throw new NotEnoughMoneyException();
        }
        this.balance -= amount;
        recipient.balance += amount;

        var transactionSender = new Transaction(amount, LocalDateTime.now(),
                "transfer_out", this);
        var transactionRecipient = new Transaction(amount, LocalDateTime.now(),
                "transfer_in", recipient);
        TransactionDAO dao = new TransactionDAO();
        return dao.makeTransaction(transactionSender, transactionRecipient);
    }

    public boolean changePassword(String newPassword) {
        UserAccountDAO dao = new UserAccountDAO();
        userAccount.setPassword(newPassword);
        return dao.updateUserAccount(userAccount);
    }

    public boolean removeAccount() {
        UserAccountDAO dao = new UserAccountDAO();
        this.account = false;
        return dao.removeUserAccount(userAccount);
    }
}

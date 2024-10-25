package com.bank.guibank.model.interfaces;

import com.bank.guibank.model.user.User;
import com.bank.guibank.model.user.exceptions.NotEnoughMoneyException;
import org.jetbrains.annotations.NotNull;

public interface UserTransactionsInterface {
    boolean deposit(double amount);
    boolean withdraw(double amount) throws NotEnoughMoneyException;
    boolean transfer(double amount, @NotNull User recipient)
            throws NotEnoughMoneyException;
}

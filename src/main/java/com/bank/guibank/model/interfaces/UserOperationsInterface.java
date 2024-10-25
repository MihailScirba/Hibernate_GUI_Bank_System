package com.bank.guibank.model.interfaces;

import com.bank.guibank.model.user.User;
import com.bank.guibank.model.user.exceptions.NotEnoughMoneyException;

public interface UserOperationsInterface {
    boolean deposit(double amount);
    boolean withdraw(double amount) throws NotEnoughMoneyException;
    boolean transfer(double amount, User recipient) throws NotEnoughMoneyException;
}

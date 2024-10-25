package com.bank.guibank.model.user.exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {}

    @Override
    public String getMessage() {
        return "Not enough money";
    }
}

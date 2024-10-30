package com.bank.guibank.model.user.exceptions;

public class UserHasAccountException extends RuntimeException {
    public UserHasAccountException(String message) {
        super(message);
    }
}

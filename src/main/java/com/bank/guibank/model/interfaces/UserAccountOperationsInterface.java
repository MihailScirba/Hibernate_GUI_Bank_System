package com.bank.guibank.model.interfaces;

public interface UserAccountOperationsInterface {
    boolean changePassword(String newPassword);
    boolean registerAccount(String username, String password);
    boolean removeAccount();
}

package com.bank.guibank.model.interfaces;

public interface UserAccountOperationsInterface {
    boolean changePassword(String newPassword);
    boolean removeAccount();
}

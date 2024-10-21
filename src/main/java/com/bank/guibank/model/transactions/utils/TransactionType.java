package com.bank.guibank.model.transactions.utils;

import lombok.Getter;

@Getter
public abstract class TransactionType {
    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAWAL = "withdrawal";
    public static final String TRANSFER_IN = "transfer_in";
    public static final String TRANSFER_OUT = "transfer_out";
}

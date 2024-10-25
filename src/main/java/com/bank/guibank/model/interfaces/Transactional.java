package com.bank.guibank.model.interfaces;

import com.bank.guibank.model.transactions.Transaction;

public interface Transactional {
    boolean makeTransaction(Transaction transaction);
    boolean makeTransaction(Transaction transfer_out, Transaction transfer_in);
}

package com.bank.guibank.model.database.transaction;

import com.bank.guibank.model.transactions.Transaction;
import com.bank.guibank.model.transactions.utils.TransactionType;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;

public class TransactionDAO {
    public boolean makeTransaction(Transaction transaction) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            try {
                session.beginTransaction();
                session.persist(transaction);
                updateUserBalance(transaction);
                session.merge(transaction.getUser());
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private void updateUserBalance(Transaction transaction) {
        if (transaction.getType().equals(TransactionType.DEPOSIT)
                || transaction.getType().equals(TransactionType.TRANSFER_IN)) {
            transaction.getUser().deposit(transaction.getAmount());
        }
        else {
            transaction.getUser().withdraw(transaction.getAmount());
        }
    }
}

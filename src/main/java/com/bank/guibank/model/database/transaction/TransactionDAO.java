package com.bank.guibank.model.database.transaction;

import com.bank.guibank.model.interfaces.Transactional;
import com.bank.guibank.model.transactions.Transaction;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;

public class TransactionDAO implements Transactional {
    @Override
    public boolean makeTransaction(Transaction transaction) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            try {
                session.beginTransaction();
                session.persist(transaction);
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

    @Override
    public boolean makeTransaction(Transaction transfer_out,
                                   Transaction transfer_in) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            try {
                session.beginTransaction();

                session.persist(transfer_out);
                session.persist(transfer_in);

                session.merge(transfer_out.getUser());
                session.merge(transfer_in.getUser());

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}

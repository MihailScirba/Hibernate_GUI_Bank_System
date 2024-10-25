package com.bank.guibank.model.database.user;

import com.bank.guibank.model.user.UserAccount;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

public class UserAccountDAO {

    public void addUserAccount(UserAccount userAccount) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()){
            try {
                session.beginTransaction();
                userAccount.getUser().setAccount(true);
                session.merge(userAccount.getUser());
                session.persist(userAccount);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    public UserAccount getUserAccountByUsername(String username) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            String hql = "from UserAccount where username=:username";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("username", username);
            return (UserAccount) query.uniqueResult();
        }
    }

    public UserAccount getUserAccountById(int id) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            String hql = "from UserAccount where user.id=:id";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("id", id);
            return (UserAccount) query.uniqueResult();
        }
    }

    public boolean updateUserAccount(UserAccount userAccount) {
        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            try {
                session.beginTransaction();
                session.merge(userAccount);
                session.getTransaction().commit();
                return true;
            } catch (Exception e)  {
                session.getTransaction().rollback();
                return false;
            }
        }
    }
}

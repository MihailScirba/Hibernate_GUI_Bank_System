package com.bank.guibank.model.database.user;

import com.bank.guibank.model.user.User;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

public class UserDAO {

    public User getUserById(int id) {
        try (Session session
                     = HibernateFactory.getSessionFactory().openSession()) {
            String hql = "from User where id=:id";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("id", id);
            session.close();
            return (User) query.uniqueResult();
        }
    }

    public User getUserByUsername(String firstname) {

        try (Session session = HibernateFactory.getSessionFactory()
                .openSession()) {
            String hql = "from User where firstname=:firstname";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("firstname", firstname);
            session.close();
            return (User) query.uniqueResult();
        }
    }

    public User getUserByFullName(String firstname, String lastname) {
         try (Session session = HibernateFactory.getSessionFactory()
                 .openSession()) {
            String hql = "from User where firstname=:firstname "
                    + "and lastname=:lastname";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("firstname", firstname);
            query.setParameter("lastname", lastname);
            session.close();
            return (User) query.uniqueResult();
        }
    }
}

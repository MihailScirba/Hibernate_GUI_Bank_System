package com.bank.guibank.model.database.user;

import com.bank.guibank.model.user.User;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

public class UserDAO {
    public User getUserByFullName(String firstname, String lastname) {
         try (Session session = HibernateFactory.getSessionFactory()
                 .openSession()) {
            String hql = "from User where firstname=:firstname "
                    + "and lastname=:lastname";
            SelectionQuery<?> query = session.createSelectionQuery(hql);
            query.setParameter("firstname", firstname);
            query.setParameter("lastname", lastname);
            return (User) query.uniqueResult();
        }
    }
}

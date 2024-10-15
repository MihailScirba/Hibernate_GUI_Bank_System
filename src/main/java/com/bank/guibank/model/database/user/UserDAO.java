package com.bank.guibank.model.database.user;

import com.bank.guibank.model.user.User;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

public class UserDAO {
    @SuppressWarnings("FieldMayBeFinal")
    private Session session = HibernateFactory.getSessionFactory().openSession();

    public User getUserByUsername(String firstname) {
        String hql = "from User where firstname=:firstname";
        SelectionQuery<?> query = session.createSelectionQuery(hql);
        query.setParameter("firstname", firstname);
        return (User) query.uniqueResult();
    }

    public User getUserById(int id) {
        String hql = "from User where id=:id";
        SelectionQuery<?> query = session.createSelectionQuery(hql);
        query.setParameter("id", id);
        return (User) query.uniqueResult();
    }
}

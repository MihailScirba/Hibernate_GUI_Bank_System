package com.bank.guibank.model.database.user;

import com.bank.guibank.model.user.UserAccount;
import com.bank.guibank.util.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

public class UserAccountDAO {
    @SuppressWarnings("FieldMayBeFinal")
    private Session session = HibernateFactory.getSessionFactory().openSession();

    public UserAccount getUserAccountByUsername(String username) {
        String hql = "from UserAccount where username=:username";
        SelectionQuery<?> query = session.createSelectionQuery(hql);
        query.setParameter("username", username);
        return (UserAccount) query.uniqueResult();
    }
}

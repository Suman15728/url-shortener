package org.example.shorturl.dao;


import org.example.shorturl.model.UrlDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsDaoImpl implements UrlDetailsDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<UrlDetails> get(String shortUrl) {
        Session session = this.sessionFactory.openSession();
        String hql = "from UrlDetails U where shortUrl=\'"+shortUrl+"\'";
        Optional<UrlDetails> result = session.createQuery(hql).stream().findAny();
        session.close();
        return result;
    }

    @Override
    public Boolean save(UrlDetails urlDetails) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(urlDetails);
        tx.commit();
        session.close();
        return true;
    }
}

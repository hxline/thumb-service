package com.hxline.thumbsservice.hibernate;

import com.hxline.thumbsservice.domain.Thumb;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Handoyo
 */
public class ThumbHibernate {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Thumb thumb) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(thumb);
        transaction.commit();
        session.close();
        closeConnection();
    }

    public List<Thumb> getAll() {
        Session session = this.sessionFactory.openSession();
        List<Thumb> thumbs = session.createQuery("FROM Thumb").list();
        session.close();
        closeConnection();
        return thumbs;
    }

    public Thumb get(String id) {
        Session session = this.sessionFactory.openSession();
        Thumb thumb = (Thumb) session.get(Thumb.class, id);
        session.close();
        closeConnection();
        return thumb;
    }
    
    public void closeConnection() {
        sessionFactory.close();
    }
}

package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.User;

import java.util.Optional;

public class UserDao {
    public void save(User user) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<User> q = session.createQuery("from User u where u.email = :e", User.class);
            q.setParameter("e", email);
            return q.uniqueResultOptional();
        }
    }
}

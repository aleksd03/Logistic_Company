package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.User;

import java.util.Optional;

/**
 * Data Access Object (DAO) for User entities.
 * Provides basic persistence and lookup operations for User.
 */
public class UserDao {

    /**
     * Persists a new User entity in the database.
     */
    public void save(User user) {
        // try-with-resources ensures the session is closed automatically
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            // Begin transaction
            Transaction transaction = session.beginTransaction();

            // Persist schedules INSERT for the User entity
            session.persist(user);

            // Commit transaction
            transaction.commit();
        }
    }

    /**
     * Finds a User by email address.
     * Returns Optional.empty() if no user is found.
     */
    public Optional<User> findByEmail(String email) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            // Create JPQL query to search user by email
            Query<User> q = session.createQuery(
                    "from User u where u.email = :e",
                    User.class
            );

            // Bind email parameter
            q.setParameter("e", email);

            // uniqueResultOptional avoids NoResultException
            return q.uniqueResultOptional();
        }
    }

    /**
     * Finds a User by its primary key.
     */
    public Optional<User> findById(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            // Wrap result in Optional to avoid null handling
            return Optional.ofNullable(session.find(User.class, id));
        }
    }
}


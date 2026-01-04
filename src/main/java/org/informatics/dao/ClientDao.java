package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Client;

import java.util.List;

public class ClientDao {

    public void save(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    public void update(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
        }
    }

    public void delete(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(client);
            tx.commit();
        }
    }

    public Client findById(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        }
    }

    public List<Client> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }
}


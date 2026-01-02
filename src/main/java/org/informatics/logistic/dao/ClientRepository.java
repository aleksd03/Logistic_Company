package org.informatics.logistic.dao;

import org.hibernate.Session;
import org.informatics.logistic.configuration.SessionFactoryUtil;
import org.informatics.logistic.entity.Client;

import java.util.List;

public class ClientRepository {

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

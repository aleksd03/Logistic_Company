package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Client;

import java.util.List;

public class ClientDao {

    public Client save(Client client) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Client merged = session.merge(client);

            tx.commit();
            System.out.println("Client saved successfully with ID: " + merged.getId());
            return merged;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR saving client: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not save client", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Client update(Client client) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Client updated = session.merge(client);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR updating client: " + e.getMessage());
            throw new RuntimeException("Could not update client", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(Client client) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Client managedClient = session.merge(client);
            session.remove(managedClient);

            tx.commit();
            System.out.println("✅ Client deleted: " + client.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting client: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete client", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Client client = session.find(Client.class, id);
            if (client != null) {
                session.remove(client);
                System.out.println("✅ Client deleted: " + id);
            } else {
                System.err.println("❌ Client not found: " + id);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting client: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete client: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Client findById(Long id) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.find(Client.class, id);
        } catch (Exception e) {
            System.err.println("ERROR finding client by ID: " + e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Client> findAll() {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT c FROM Client c " +
                                    "LEFT JOIN FETCH c.user " +
                                    "LEFT JOIN FETCH c.company",
                            Client.class)
                    .list();
        } catch (Exception e) {
            System.err.println("ERROR finding all clients: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Client findByUserId(Long userId) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT c FROM Client c JOIN c.user u WHERE u.id = :userId",
                            Client.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("ERROR finding client by user ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Shipment;

import java.util.List;

public class ShipmentDao {
    public Shipment save(Shipment shipment) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(shipment);
            tx.commit();
            System.out.println("Shipment saved successfully with ID: " + shipment.getId());
            return shipment;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR saving shipment: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not save shipment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Shipment update(Shipment shipment) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Shipment updated = session.merge(shipment);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR updating shipment: " + e.getMessage());
            throw new RuntimeException("Could not update shipment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(Shipment shipment) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.remove(shipment);
            tx.commit();
            System.out.println("Shipment deleted: " + shipment.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR deleting shipment: " + e.getMessage());
            throw new RuntimeException("Could not delete shipment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Shipment findById(Long id) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "WHERE s.id = :id",
                            Shipment.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("ERROR finding shipment by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Shipment> findAll() {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "ORDER BY s.registrationDate DESC",
                            Shipment.class)
                    .list();
        } catch (Exception e) {
            System.err.println("ERROR finding all shipments: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Shipment> findBySenderId(Long senderId) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "WHERE s.sender.id = :senderId " +
                                    "ORDER BY s.registrationDate DESC",
                            Shipment.class)
                    .setParameter("senderId", senderId)
                    .list();
        } catch (Exception e) {
            System.err.println("Error finding shipments by sender ID: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Shipment> findByReceiverId(Long receiverId) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "WHERE s.receiver.id = :receiverId " +
                                    "ORDER BY s.registrationDate DESC",
                            Shipment.class)
                    .setParameter("receiverId", receiverId)
                    .list();
        } catch (Exception e) {
            System.err.println("Error finding shipments by receiver ID: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Shipment> findByRegisteredBy(Long employeeId) {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "WHERE s.registeredBy.id = :employeeId " +
                                    "ORDER BY s.registrationDate DESC",
                            Shipment.class)
                    .setParameter("employeeId", employeeId)
                    .list();
        } catch (Exception e) {
            System.err.println("Error finding shipments by employee ID: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Shipment> findUndelivered() {
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            return session.createQuery(
                            "SELECT DISTINCT s FROM Shipment s " +
                                    "LEFT JOIN FETCH s.sender sender " +
                                    "LEFT JOIN FETCH sender.user " +
                                    "LEFT JOIN FETCH s.receiver receiver " +
                                    "LEFT JOIN FETCH receiver.user " +
                                    "LEFT JOIN FETCH s.registeredBy employee " +
                                    "LEFT JOIN FETCH employee.user " +
                                    "LEFT JOIN FETCH s.deliveryOffice " +
                                    "WHERE s.status = 'SENT' " +
                                    "ORDER BY s.registrationDate DESC",
                            Shipment.class)
                    .list();
        } catch (Exception e) {
            System.err.println("Error finding undelivered shipments: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
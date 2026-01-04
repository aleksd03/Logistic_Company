package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Shipment;
import org.informatics.entity.ShipmentStatus;

import java.util.List;

public class ShipmentDao {

    public void save(Shipment shipment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(shipment);
            tx.commit();
        }
    }

    public void update(Shipment shipment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(shipment);
            tx.commit();
        }
    }

    public void delete(Shipment shipment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(shipment);
            tx.commit();
        }
    }

    public Shipment findById(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Shipment.class, id);
        }
    }

    public List<Shipment> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Shipment", Shipment.class).list();
        }
    }

    // Requirement 5.a – sent but not yet received
    public List<Shipment> findSentNotReceived() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Shipment s where s.status = :st",
                    Shipment.class
            ).setParameter("st", ShipmentStatus.SENT)
             .list();
        }
    }

    // Requirement 6 – shipments registered by a specific employee
    public List<Shipment> findByEmployee(Long employeeId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Shipment s where s.registeredBy.id = :eid",
                    Shipment.class
            ).setParameter("eid", employeeId)
             .list();
        }
    }

    // Requirement 7 – shipments sent or received by a client
    public List<Shipment> findByClient(Long clientId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Shipment s where s.sender.id = :cid or s.receiver.id = :cid",
                    Shipment.class
            ).setParameter("cid", clientId)
             .list();
        }
    }

    // Requirement 5.h – total revenue from received shipments
    public Double totalRevenue() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select sum(s.price) from Shipment s where s.status = :st",
                    Double.class
            ).setParameter("st", ShipmentStatus.RECEIVED)
             .uniqueResult();
        }
    }
}


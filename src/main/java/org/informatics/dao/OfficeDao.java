package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Office;

import java.util.List;

public class OfficeDao {
    public Office save(Office office) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(office);
            tx.commit();
            System.out.println("Office saved successfully with ID: " + office.getId());
            return office;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR saving office: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not save office", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Office update(Office office) {
        Transaction tx = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Office updated = session.merge(office);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR updating office: " + e.getMessage());
            throw new RuntimeException("Could not update office", e);
        }
    }

    public void delete(Office office) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Office managedOffice = session.merge(office);
            session.remove(managedOffice);

            tx.commit();
            System.out.println("✅ Office deleted: " + office.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting office: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete office", e);
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

            Office office = session.find(Office.class, id);
            if (office != null) {
                session.remove(office);
                System.out.println("✅ Office deleted: " + id);
            } else {
                System.err.println("❌ Office not found: " + id);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting office: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete office: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Office findById(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Office.class, id);
        } catch (Exception e) {
            System.err.println("ERROR finding office by ID: " + e.getMessage());
            return null;
        }
    }

    public List<Office> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Office ORDER BY id", Office.class).list();
        } catch (Exception e) {
            System.err.println("ERROR finding all offices: " + e.getMessage());
            return List.of();
        }
    }

    public List<Office> findByCompanyId(Long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Office o WHERE o.company.id = :companyId ORDER BY o.address",
                            Office.class)
                    .setParameter("companyId", companyId)
                    .list();
        } catch (Exception e) {
            System.err.println("ERROR finding offices by company ID: " + e.getMessage());
            return List.of();
        }
    }
}
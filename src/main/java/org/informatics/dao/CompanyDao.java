package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Company;

import java.util.List;

public class CompanyDao {
    public Company save(Company company) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(company);
            tx.commit();
            System.out.println("Company saved successfully with ID: " + company.getId());
            return company;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR saving company: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not save company", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Company update(Company company) {
        Transaction tx = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Company updated = session.merge(company);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR updating company: " + e.getMessage());
            throw new RuntimeException("Could not update company", e);
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Company company = session.find(Company.class, id);
            if (company != null) {
                session.remove(company);  // CASCADE ще изтрие offices
                System.out.println("✅ Company deleted (cascade to offices): " + id);
            } else {
                System.err.println("❌ Company not found: " + id);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting company: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete company: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Company findById(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Company.class, id);
        } catch (Exception e) {
            System.err.println("ERROR finding company by ID: " + e.getMessage());
            return null;
        }
    }

    public Company findByName(String name) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Company c WHERE c.name = :name",
                            Company.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("ERROR finding company by name: " + e.getMessage());
            return null;
        }
    }

    public List<Company> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company", Company.class).list();
        } catch (Exception e) {
            System.err.println("ERROR finding all companies: " + e.getMessage());
            return List.of();
        }
    }
}
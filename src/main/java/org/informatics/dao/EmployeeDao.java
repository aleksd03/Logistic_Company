package org.informatics.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Employee;

import java.util.List;

public class EmployeeDao {
    public Employee save(Employee employee) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Employee merged = session.merge(employee);

            tx.commit();
            System.out.println("Employee saved successfully with ID: " + merged.getId());
            return merged;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("ERROR saving employee: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not save employee", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Employee update(Employee employee) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Employee updated = session.merge(employee);

            tx.commit();
            System.out.println("✅ Employee updated: " + updated.getId());
            return updated;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR updating employee: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not update employee", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Employee findById(Long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.find(Employee.class, id);
        }
    }

    public Employee findByUserId(Long userId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Employee e WHERE e.user.id = :userId",
                            Employee.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("Error finding employee by user ID: " + e.getMessage());
            return null;
        }
    }

    public List<Employee> findAll() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    public void delete(Employee employee) {
        Transaction tx = null;
        Session session = null;
        try {
            session = SessionFactoryUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Employee managedEmployee = session.merge(employee);
            session.remove(managedEmployee);

            tx.commit();
            System.out.println("✅ Employee deleted: " + employee.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting employee: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete employee", e);
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

            Employee employee = session.find(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
                System.out.println("✅ Employee deleted: " + id);
            } else {
                System.err.println("❌ Employee not found: " + id);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("❌ ERROR deleting employee: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not delete employee: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

package org.informatics.dao;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.logistic.entity.Employee;

public class EmployeeDao {

    public Employee findByUserId(Long userId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Employee e where e.user.id = :uid",
                    Employee.class
            ).setParameter("uid", userId)
             .uniqueResult();
        }
    }
}

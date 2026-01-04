package org.informatics.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.Company;

public class CompanyService {

    public Company getDefaultCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            Company company = session
                    .createQuery("from Company", Company.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (company != null) {
                return company;
            }

            Transaction tx = session.beginTransaction();
            Company c = new Company();
            c.setName("ALVAS Logistics");
            session.persist(c);
            tx.commit();

            return c;
        }
    }
}

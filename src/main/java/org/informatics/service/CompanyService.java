package org.informatics.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.logistic.entity.LogisticCompany;

public class CompanyService {

    public LogisticCompany getDefaultCompany() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            LogisticCompany company = session
                    .createQuery("from LogisticCompany", LogisticCompany.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (company != null) {
                return company;
            }

            Transaction tx = session.beginTransaction();
            LogisticCompany c = new LogisticCompany();
            c.setName("ALVAS Logistics");
            session.persist(c);
            tx.commit();

            return c;
        }
    }
}

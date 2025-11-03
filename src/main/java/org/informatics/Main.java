package org.informatics;

import org.hibernate.Session;
import org.informatics.logistic.configuration.SessionFactoryUtil;

public class Main {
    public static void main(String[] args) {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
    }
}
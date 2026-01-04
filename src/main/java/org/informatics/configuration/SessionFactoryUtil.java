package org.informatics.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// Entities
import org.informatics.entity.User;
import org.informatics.entity.Client;
import org.informatics.entity.Employee;
import org.informatics.entity.Company;
import org.informatics.entity.Office;
import org.informatics.entity.Shipment;

public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            Configuration configuration = new Configuration();

            // ---- REGISTER ALL ENTITIES USED IN THE PROJECT ----
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Office.class);
            configuration.addAnnotatedClass(Shipment.class);

            // ---- BUILD SESSION FACTORY ----
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}


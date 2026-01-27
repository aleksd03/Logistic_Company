package org.informatics.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.informatics.entity.*;

import java.util.Properties;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Зареди hibernate.properties
                Properties properties = new Properties();
                properties.load(SessionFactoryUtil.class.getClassLoader()
                        .getResourceAsStream("hibernate.properties"));

                configuration.setProperties(properties);

                // Добави entity класовете
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Office.class);
                configuration.addAnnotatedClass(Shipment.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                System.out.println("✅ Hibernate SessionFactory created successfully from hibernate.properties!");

            } catch (Exception e) {
                System.err.println("❌ ERROR creating SessionFactory: " + e.getMessage());
                e.printStackTrace();
                throw new ExceptionInInitializerError("Failed to create SessionFactory: " + e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }
}
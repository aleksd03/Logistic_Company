package org.informatics.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.informatics.entity.*;

import java.util.Properties;

/**
 * Utility class responsible for creating and managing
 * a single Hibernate SessionFactory instance.
 *
 * SessionFactory is expensive to create and should exist
 * as a singleton for the entire application lifecycle.
 */
public class SessionFactoryUtil {

    // Singleton instance of SessionFactory
    private static SessionFactory sessionFactory;

    /**
     * Lazily initializes and returns the Hibernate SessionFactory.
     * The factory is created only once and reused afterwards.
     */
    public static SessionFactory getSessionFactory() {

        // Create SessionFactory only if it does not already exist
        if (sessionFactory == null) {
            try {
                // Hibernate Configuration object (programmatic configuration)
                Configuration configuration = new Configuration();

                // Load properties from hibernate.properties file
                // (database connection, dialect, show_sql, etc.)
                Properties properties = new Properties();
                properties.load(
                        SessionFactoryUtil.class
                                .getClassLoader()
                                .getResourceAsStream("hibernate.properties")
                );

                // Apply loaded properties to Hibernate configuration
                configuration.setProperties(properties);

                // Register all @Entity-annotated classes explicitly
                // Hibernate needs to know which classes map to DB tables
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Office.class);
                configuration.addAnnotatedClass(Shipment.class);

                // Build ServiceRegistry using the applied settings
                // This represents Hibernate's internal service layer
                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build();

                // Build the SessionFactory using the configuration and service registry
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                // Log successful initialization
                System.out.println("✅ Hibernate SessionFactory created successfully from hibernate.properties!");

            } catch (Exception e) {
                // Log the error and fail fast – application cannot run without SessionFactory
                System.err.println("❌ ERROR creating SessionFactory: " + e.getMessage());
                e.printStackTrace();

                // Prevent application from starting in an invalid state
                throw new ExceptionInInitializerError(
                        "Failed to create SessionFactory: " + e
                );
            }
        }

        // Return existing or newly created SessionFactory
        return sessionFactory;
    }

    /**
     * Closes the SessionFactory and releases all database resources.
     * Should be called on application shutdown.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }
}

        }
    }
}

package org.informatics.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.ShipmentDto;
import org.informatics.entity.Shipment;

import java.util.List;

/**
 * Service responsible for read-only shipment queries
 * and mapping Shipment entities to ShipmentDto objects.
 */
public class ShipmentQueryService {

    /**
     * Returns all shipments in the system mapped to DTOs.
     * Intended for employee-facing views.
     */
    public List<ShipmentDto> getAllForEmployee() {
        // Open Hibernate session (read-only operation)
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            // Load all Shipment entities
            List<Shipment> list =
                    session.createQuery("from Shipment", Shipment.class).list();

            // Convert entities to DTOs
            return list.stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    /**
     * Returns all shipments where the given email belongs
     * to either the sender or the receiver.
     */
    public List<ShipmentDto> getForClientEmail(String email) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            // JPQL query matching sender or receiver email (case-insensitive)
            Query<Shipment> q = session.createQuery(
                    "from Shipment s " +
                            "where lower(s.sender.user.email) = :e " +
                            "   or lower(s.receiver.user.email) = :e",
                    Shipment.class
            );

            // Bind normalized email parameter
            q.setParameter("e", email.toLowerCase());

            // Execute query and map results to DTOs
            return q.list().stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    /**
     * Maps a Shipment entity to a ShipmentDto.
     * Handles possible null associations safely.
     */
    private ShipmentDto toDto(Shipment s) {

        // Safely extract sender email
        String sender = s.getSender() == null ? "" :
                (s.getSender().getUser() == null ? "" :
                        s.getSender().getUser().getEmail());

        // Safely extract receiver email
        String receiver = s.getReceiver() == null ? "" :
                (s.getReceiver().getUser() == null ? "" :
                        s.getReceiver().getUser().getEmail());

        // Extract shipment status
        String status = s.getStatus() == null ? "" : s.getStatus().name();

        // Create DTO with selected fields
        return new ShipmentDto(
                s.getId(),
                sender,
                receiver,
                status,
                s.getPrice()
        );
    }
}


package org.informatics.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.ShipmentDto;
import org.informatics.entity.Shipment;

import java.util.List;

public class ShipmentQueryService {
    public List<ShipmentDto> getAllForEmployee() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            List<Shipment> list = session.createQuery("from Shipment", Shipment.class).list();
            return list.stream().map(this::toDto).toList();
        }
    }

    public List<ShipmentDto> getForClientEmail(String email) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Shipment> q = session.createQuery(
                    "from Shipment s " +
                            "where lower(s.sender.user.email) = :e " +
                            "   or lower(s.receiver.user.email) = :e",
                    Shipment.class
            );
            q.setParameter("e", email.toLowerCase());
            return q.list().stream().map(this::toDto).toList();
        }
    }

    private ShipmentDto toDto(Shipment s) {
        String sender = s.getSender() == null ? "" :
                (s.getSender().getUser() == null ? "" : s.getSender().getUser().getEmail());
        String receiver = s.getReceiver() == null ? "" :
                (s.getReceiver().getUser() == null ? "" : s.getReceiver().getUser().getEmail());
        String status = s.getStatus() == null ? "" : s.getStatus().name();

        return new ShipmentDto(
                s.getId(),
                sender,
                receiver,
                status,
                s.getPrice()
        );
    }
}

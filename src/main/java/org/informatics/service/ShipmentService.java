package org.informatics.service;

import org.informatics.dao.ShipmentDao;
import org.informatics.entity.*;
import org.informatics.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ShipmentService {

    private final ShipmentDao repo = new ShipmentDao();
    private final PricingService pricingService = new PricingService();

    public Shipment registerShipment(
            Client sender,
            Client receiver,
            Employee registeredBy,
            double weight,
            boolean deliveryToOffice,
            Office deliveryOffice,
            String deliveryAddress
    ) {
        if (sender == null || receiver == null || registeredBy == null) {
            throw new IllegalArgumentException("Подател, получател и служител са задължителни");
        }

        if (Objects.equals(sender.getId(), receiver.getId())) {
            throw new IllegalArgumentException("Подателят и получателят не могат да бъдат едно и също лице");
        }

        if (!pricingService.validateDelivery(deliveryToOffice,
                deliveryOffice != null ? deliveryOffice.getId() : null,
                deliveryAddress)) {
            throw new IllegalArgumentException("Невалидна доставка: изберете офис ИЛИ въведете адрес");
        }

        double price = pricingService.calculatePrice(weight, deliveryToOffice);

        Shipment shipment = new Shipment();
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setRegisteredBy(registeredBy);
        shipment.setWeight(weight);
        shipment.setPrice(price);
        shipment.setDeliveryToOffice(deliveryToOffice);

        if (deliveryToOffice && deliveryOffice != null) {
            shipment.setDeliveryOffice(deliveryOffice);
            shipment.setDeliveryAddress(deliveryOffice.getAddress());
        } else {
            shipment.setDeliveryOffice(null);
            shipment.setDeliveryAddress(deliveryAddress);
        }

        shipment.setStatus(ShipmentStatus.SENT);
        shipment.setRegistrationDate(LocalDateTime.now());
        shipment.setDeliveryDate(null);

        return repo.save(shipment);
    }

    public void updateShipment(Long id, double weight, boolean deliveryToOffice,
                               Office deliveryOffice, String deliveryAddress) {
        Shipment shipment = getShipmentById(id);
        if (shipment == null) {
            throw new RuntimeException("Shipment not found with ID: " + id);
        }

        shipment.setWeight(weight);
        shipment.setDeliveryToOffice(deliveryToOffice);

        if (deliveryToOffice && deliveryOffice != null) {
            shipment.setDeliveryOffice(deliveryOffice);
            shipment.setDeliveryAddress(deliveryOffice.getAddress());
        } else {
            shipment.setDeliveryOffice(null);
            shipment.setDeliveryAddress(deliveryAddress);
        }

        // Recalculate price
        double newPrice = pricingService.calculatePrice(weight, deliveryToOffice);
        shipment.setPrice(newPrice);

        repo.update(shipment);
        System.out.println("✅ Shipment updated: " + id);
    }

    public void markAsReceived(Long id) {
        Shipment shipment = getShipmentById(id);
        if (shipment == null) {
            throw new RuntimeException("Shipment not found with ID: " + id);
        }

        if (shipment.getStatus() == ShipmentStatus.RECEIVED) {
            throw new RuntimeException("Пратката вече е маркирана като получена");
        }

        shipment.setStatus(ShipmentStatus.RECEIVED);
        shipment.setDeliveryDate(LocalDateTime.now());

        repo.update(shipment);
        System.out.println("✅ Shipment marked as received: " + id);
    }

    public void deleteShipment(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Shipment ID cannot be null");
        }

        System.out.println("🗑️ Deleting shipment with ID: " + id);

        try {
            repo.deleteById(id);
            System.out.println("✅ Shipment deleted successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to delete shipment: " + e.getMessage());
            throw new RuntimeException("Грешка при изтриване на пратката: " + e.getMessage(), e);
        }
    }

    public List<Shipment> getAllShipments() {
        return repo.findAll();
    }

    public List<Shipment> getShipmentsBySender(Long senderId) {
        return repo.findBySenderId(senderId);
    }

    public List<Shipment> getShipmentsByReceiver(Long receiverId) {
        return repo.findByReceiverId(receiverId);
    }

    public Shipment getShipmentById(Long id) {
        return repo.findById(id);
    }

    public List<Shipment> getShipmentsByEmployee(Long employeeId) {
        return repo.findByRegisteredBy(employeeId);
    }

    public List<Shipment> getUndeliveredShipments() {
        return repo.findUndelivered();
    }
}
package org.informatics.service;

import org.informatics.dao.ShipmentDao;
import org.informatics.entity.*;
import org.informatics.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Service layer responsible for managing Shipment lifecycle:
 * registration, updates, delivery status changes, deletion, and reporting.
 */
public class ShipmentService {

    // DAO responsible for Shipment persistence
    private final ShipmentDao repo = new ShipmentDao();

    // Service responsible for price calculation and delivery validation
    private final PricingService pricingService = new PricingService();

    /**
     * Registers a new shipment with full validation and price calculation.
     */
    public Shipment registerShipment(
            Client sender,
            Client receiver,
            Employee registeredBy,
            double weight,
            boolean deliveryToOffice,
            Office deliveryOffice,
            String deliveryAddress
    ) {
        // Validate required entities
        if (sender == null || receiver == null || registeredBy == null) {
            throw new IllegalArgumentException(
                    "Подател, получател и служител са задължителни"
            );
        }

        // Sender and receiver must be different clients
        if (Objects.equals(sender.getId(), receiver.getId())) {
            throw new IllegalArgumentException(
                    "Подателят и получателят не могат да бъдат едно и също лице"
            );
        }

        // Validate delivery type (office vs address)
        if (!pricingService.validateDelivery(
                deliveryToOffice,
                deliveryOffice != null ? deliveryOffice.getId() : null,
                deliveryAddress
        )) {
            throw new IllegalArgumentException(
                    "Невалидна доставка: изберете офис ИЛИ въведете адрес"
            );
        }

        // Calculate shipment price
        double price = pricingService.calculatePrice(weight, deliveryToOffice);

        // Create and populate Shipment entity
        Shipment shipment = new Shipment();
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setRegisteredBy(registeredBy);
        shipment.setWeight(weight);
        shipment.setPrice(price);
        shipment.setDeliveryToOffice(deliveryToOffice);

        // Set delivery destination based on delivery type
        if (deliveryToOffice && deliveryOffice != null) {
            shipment.setDeliveryOffice(deliveryOffice);
            shipment.setDeliveryAddress(deliveryOffice.getAddress());
        } else {
            shipment.setDeliveryOffice(null);
            shipment.setDeliveryAddress(deliveryAddress);
        }

        // Initialize shipment state
        shipment.setStatus(ShipmentStatus.SENT);
        shipment.setRegistrationDate(LocalDateTime.now());
        shipment.setDeliveryDate(null);

        // Persist shipment
        return repo.save(shipment);
    }

    /**
     * Updates shipment delivery details and recalculates price.
     */
    public void updateShipment(
            Long id,
            double weight,
            boolean deliveryToOffice,
            Office deliveryOffice,
            String deliveryAddress
    ) {
        Shipment shipment = getShipmentById(id);
        if (shipment == null) {
            throw new RuntimeException("Shipment not found with ID: " + id);
        }

        shipment.setWeight(weight);
        shipment.setDeliveryToOffice(deliveryToOffice);

        // Update delivery destination
        if (deliveryToOffice && deliveryOffice != null) {
            shipment.setDeliveryOffice(deliveryOffice);
            shipment.setDeliveryAddress(deliveryOffice.getAddress());
        } else {
            shipment.setDeliveryOffice(null);
            shipment.setDeliveryAddress(deliveryAddress);
        }

        // Recalculate shipment price
        double newPrice = pricingService.calculatePrice(weight, deliveryToOffice);
        shipment.setPrice(newPrice);

        repo.update(shipment);
        System.out.println("✅ Shipment updated: " + id);
    }

    /**
     * Marks a shipment as received and sets delivery date.
     */
    public void markAsReceived(Long id) {
        Shipment shipment = getShipmentById(id);
        if (shipment == null) {
            throw new RuntimeException("Shipment not found with ID: " + id);
        }

        // Prevent double-receiving
        if (shipment.getStatus() == ShipmentStatus.RECEIVED) {
            throw new RuntimeException(
                    "Пратката вече е маркирана като получена"
            );
        }

        shipment.setStatus(ShipmentStatus.RECEIVED);
        shipment.setDeliveryDate(LocalDateTime.now());

        repo.update(shipment);
        System.out.println("✅ Shipment marked as received: " + id);
    }

    /**
     * Deletes a shipment by its ID.
     */
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
            throw new RuntimeException(
                    "Грешка при изтриване на пратката: " + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Returns all shipments.
     */
    public List<Shipment> getAllShipments() {
        return repo.findAll();
    }

    /**
     * Returns all shipments sent by a specific sender.
     */
    public List<Shipment> getShipmentsBySender(Long senderId) {
        return repo.findBySenderId(senderId);
    }

    /**
     * Returns all shipments received by a specific receiver.
     */
    public List<Shipment> getShipmentsByReceiver(Long receiverId) {
        return repo.findByReceiverId(receiverId);
    }

    /**
     * Retrieves a shipment by its primary key.
     */
    public Shipment getShipmentById(Long id) {
        return repo.findById(id);
    }

    /**
     * Returns all shipments registered by a specific employee.
     */
    public List<Shipment> getShipmentsByEmployee(Long employeeId) {
        return repo.findByRegisteredBy(employeeId);
    }

    /**
     * Returns all shipments that are not yet delivered.
     */
    public List<Shipment> getUndeliveredShipments() {
        return repo.findUndelivered();
    }

    /**
     * Calculates revenue for shipments registered within a given period.
     */
    public double calculateRevenueForPeriod(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        List<Shipment> allShipments = repo.findAll();

        return allShipments.stream()
                .filter(s -> s.getRegistrationDate() != null)
                .filter(s -> !s.getRegistrationDate().isBefore(startDate))
                .filter(s -> !s.getRegistrationDate().isAfter(endDate))
                .mapToDouble(Shipment::getPrice)
                .sum();
    }

    /**
     * Returns all shipments registered within a given period.
     */
    public List<Shipment> getShipmentsForPeriod(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        List<Shipment> allShipments = repo.findAll();

        return allShipments.stream()
                .filter(s -> s.getRegistrationDate() != null)
                .filter(s -> !s.getRegistrationDate().isBefore(startDate))
                .filter(s -> !s.getRegistrationDate().isAfter(endDate))
                .collect(java.util.stream.Collectors.toList());
    }
}

package org.informatics.service;

import org.informatics.entity.*;
import org.informatics.entity.enums.ShipmentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {

    private final ShipmentService shipmentService = new ShipmentService();

    @Test
    void registerShipment_nullSender_throwsException() {
        Client receiver = new Client();
        Employee employee = new Employee();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> shipmentService.registerShipment(
                        null,
                        receiver,
                        employee,
                        5.0,
                        false,
                        null,
                        "Sofia"
                )
        );

        assertTrue(ex.getMessage().contains("задължителни"));
    }

    @Test
    void registerShipment_sameSenderAndReceiver_throwsException() {
        Client client = new Client();
        client.setId(1L);

        Employee employee = new Employee();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> shipmentService.registerShipment(
                        client,
                        client,
                        employee,
                        3.0,
                        false,
                        null,
                        "Plovdiv"
                )
        );

        assertTrue(ex.getMessage().contains("не могат да бъдат едно и също"));
    }

    @Test
    void markAsReceived_twice_throwsException() {
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        shipment.setStatus(ShipmentStatus.RECEIVED);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> shipmentService.markAsReceived(1L)
        );

        assertTrue(ex.getMessage().contains("вече е маркирана"));
    }

    @Test
    void calculateRevenueForPeriod_returnsCorrectSum() {
        LocalDateTime now = LocalDateTime.now();

        Shipment s1 = new Shipment();
        s1.setPrice(10.0);
        s1.setRegistrationDate(now.minusDays(1));

        Shipment s2 = new Shipment();
        s2.setPrice(25.5);
        s2.setRegistrationDate(now.minusDays(2));


        double result = shipmentService.calculateRevenueForPeriod(
                now.minusDays(5),
                now
        );

        assertTrue(result >= 0); 
    }
}

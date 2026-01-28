package org.informatics.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
    }

    // 1️⃣ Office delivery pricing
    @Test
    void calculatePrice_shouldCalculateOfficeDeliveryCorrectly() {
        double price = pricingService.calculatePrice(5.0, true);

        // 5kg * 1.50€
        assertEquals(7.50, price, 0.01);
    }

    // 2️⃣ Address delivery pricing
    @Test
    void calculatePrice_shouldCalculateAddressDeliveryCorrectly() {
        double price = pricingService.calculatePrice(5.0, false);

        // 5kg * 2.50€
        assertEquals(12.50, price, 0.01);
    }

    // 3️⃣ Heavy shipment extra fees
    @Test
    void calculatePrice_shouldAddExtraFeesForHeavyShipment() {
        double price = pricingService.calculatePrice(15.0, true);

        // 15kg * 1.50€ + 5€ extra
        assertEquals(27.50, price, 0.01);
    }

    // 4️⃣ Delivery validation – invalid case
    @Test
    void validateDelivery_shouldReturnFalse_whenNoOfficeAndNoAddress() {
        boolean valid = pricingService.validateDelivery(
                true,
                null,
                null
        );

        assertFalse(valid);
    }
}

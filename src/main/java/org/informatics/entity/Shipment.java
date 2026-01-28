package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;

/**
 * Entity representing a Shipment.
 * A Shipment links sender and receiver clients, may be registered
 * by an employee, and can be delivered either to an office or an address.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {

    /**
     * Client who sends the shipment.
     * - Optional
     * - If the client is deleted, sender is set to NULL
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "sender_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_sender",
                    foreignKeyDefinition =
                            "FOREIGN KEY (sender_id) REFERENCES clients(id) ON DELETE SET NULL"
            )
    )
    private Client sender;

    /**
     * Client who receives the shipment.
     * - Optional
     * - If the client is deleted, receiver is set to NULL
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "receiver_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_receiver",
                    foreignKeyDefinition =
                            "FOREIGN KEY (receiver_id) REFERENCES clients(id) ON DELETE SET NULL"
            )
    )
    private Client receiver;

    /**
     * Employee who registered the shipment in the system.
     * - Optional
     * - If the employee is deleted, reference is set to NULL
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "registered_by_employee_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_registered_by",
                    foreignKeyDefinition =
                            "FOREIGN KEY (registered_by_employee_id) REFERENCES employees(id) ON DELETE SET NULL"
            )
    )
    private Employee registeredBy;

    /**
     * Weight of the shipment.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Double weight;

    /**
     * Price of the shipment.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Indicates whether the shipment is delivered to an office (true)
     * or to a specific address (false).
     */
    @Column(name = "delivery_to_office", nullable = false)
    private Boolean deliveryToOffice;

    /**
     * Office where the shipment will be delivered (if deliveryToOffice = true).
     * Optional.
     */
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "delivery_office_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_delivery_office",
                    foreignKeyDefinition =
                            "FOREIGN KEY (delivery_office_id) REFERENCES offices(id) ON DELETE SET NULL"
            )
    )
    private Office deliveryOffice;

    /**
     * Address where the shipment will be delivered
     * (used when deliveryToOffice = false).
     */
    @Column(name = "delivery_address", length = 500, nullable = true)
    private String deliveryAddress;

    /**
     * Current status of the shipment.
     * Stored as STRING for readability and stability.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    /**
     * Date and time when the shipment was registered.
     */
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    /**
     * Date and time when the shipment was delivered.
     * Null if the shipment is not yet delivered.
     */
    @Column(name = "delivery_date", nullable = true)
    private LocalDateTime deliveryDate;
}

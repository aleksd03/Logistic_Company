package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "sender_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_sender",
                    foreignKeyDefinition = "FOREIGN KEY (sender_id) REFERENCES clients(id) ON DELETE SET NULL"
            )
    )
    private Client sender;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "receiver_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_receiver",
                    foreignKeyDefinition = "FOREIGN KEY (receiver_id) REFERENCES clients(id) ON DELETE SET NULL"
            )
    )
    private Client receiver;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "registered_by_employee_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_registered_by",
                    foreignKeyDefinition = "FOREIGN KEY (registered_by_employee_id) REFERENCES employees(id) ON DELETE SET NULL"
            )
    )
    private Employee registeredBy;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double price;

    @Column(name = "delivery_to_office", nullable = false)
    private Boolean deliveryToOffice;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "delivery_office_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_shipment_delivery_office",
                    foreignKeyDefinition = "FOREIGN KEY (delivery_office_id) REFERENCES offices(id) ON DELETE SET NULL"
            )
    )
    private Office deliveryOffice;

    @Column(name = "delivery_address", length = 500, nullable = true)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "delivery_date", nullable = true)
    private LocalDateTime deliveryDate;
}
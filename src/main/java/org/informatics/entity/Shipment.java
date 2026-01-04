package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Client who sends the shipment
    @ManyToOne(optional = false)
    private Client sender;

    // Client who receives the shipment
    @ManyToOne(optional = false)
    private Client receiver;

    // Employee who registered the shipment
    @ManyToOne(optional = false)
    private Employee registeredBy;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;
}

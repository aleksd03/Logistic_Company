package org.informatics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /* ---------- Getters & Setters ---------- */

    public Long getId() {
        return id;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    public Employee getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(Employee registeredBy) {
        this.registeredBy = registeredBy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}

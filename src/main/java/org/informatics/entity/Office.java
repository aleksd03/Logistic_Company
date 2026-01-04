package org.informatics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "offices")
public class Office extends BaseEntity {

    @Column(nullable = false)
    private String address;

    @ManyToOne(optional = false)
    private Company company;

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}


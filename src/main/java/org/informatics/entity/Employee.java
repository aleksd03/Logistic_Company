package org.informatics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(optional = false)
    private Office office;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }
}

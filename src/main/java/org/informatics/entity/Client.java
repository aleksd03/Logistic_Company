package org.informatics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

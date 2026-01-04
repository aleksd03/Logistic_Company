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
@Table(name = "offices")
public class Office extends BaseEntity {

    @Column(nullable = false)
    private String address;

    @ManyToOne(optional = false)
    private Company company;
}


package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a Company Office.
 * Each Office belongs to exactly one Company.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offices")
public class Office extends BaseEntity {

    /**
     * Physical address of the office.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Many offices belong to one company.
     * - Association is mandatory
     */
    @ManyToOne(optional = false)
    private Company company;
}



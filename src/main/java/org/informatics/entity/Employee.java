package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.enums.EmployeeType;

/**
 * Entity representing an Employee in the system.
 * Each Employee is associated with exactly one User
 * and may optionally be linked to a Company and an Office.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    /**
     * One-to-one relationship with User.
     * - Each Employee must have exactly one User
     * - CascadeType.ALL propagates all persistence operations
     * - orphanRemoval ensures User is deleted if Employee is removed
     */
    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    /**
     * Many Employees may belong to one Company.
     * - Association is optional
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    /**
     * Many Employees may be assigned to one Office.
     * - Association is optional
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "office_id", nullable = true)
    private Office office;

    /**
     * Type of the employee (e.g. COURIER, OPERATOR, MANAGER).
     * Stored as STRING for readability and stability.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = true)
    private EmployeeType employeeType;
}

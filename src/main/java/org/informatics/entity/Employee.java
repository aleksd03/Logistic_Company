package org.informatics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.enums.EmployeeType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @ManyToOne(optional = true)
    @JoinColumn(name = "office_id", nullable = true)
    private Office office;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = true)
    private EmployeeType employeeType;
}
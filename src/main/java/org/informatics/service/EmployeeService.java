package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.entity.Employee;
import org.informatics.entity.Company;
import org.informatics.entity.Office;
import org.informatics.entity.User;

import java.util.List;

public class EmployeeService {
    private final EmployeeDao repo = new EmployeeDao();

    public Employee createForUser(User user, Company company, Office office) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Employee e = new Employee();
        e.setUser(user);
        e.setCompany(company);
        e.setOffice(office);

        Employee saved = repo.save(e);
        System.out.println("Employee created for user: " + user.getEmail());

        return saved;
    }

    public Employee getEmployeeByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return repo.findByUserId(userId);
    }

    public Employee getEmployeeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return repo.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        System.out.println("📝 Updating employee: " + employee.getId());
        return repo.update(employee);
    }

    public void deleteEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        System.out.println("🗑️ Deleting employee with ID: " + id);

        try {
            repo.deleteById(id);
            System.out.println("✅ Employee deleted successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to delete employee: " + e.getMessage());
            throw new RuntimeException("Грешка при изтриване на служителя: " + e.getMessage(), e);
        }
    }
}
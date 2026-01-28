package org.informatics.service;

import org.informatics.dao.OfficeDao;
import org.informatics.entity.Company;
import org.informatics.entity.Office;

import java.util.List;

/**
 * Service layer for Office-related operations.
 * Handles validation and delegates persistence to OfficeDao.
 */
public class OfficeService {

    // DAO responsible for Office persistence
    private final OfficeDao repo = new OfficeDao();

    /**
     * Creates and persists a new Office for a given Company.
     */
    public Office createOffice(String address, Company company) {

        // Validate office address
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Адресът не може да бъде празен");
        }

        // Validate company association
        if (company == null) {
            throw new IllegalArgumentException("Компанията не може да бъде null");
        }

        // Prevent duplicate office addresses
        Office existing = repo.findByAddress(address.trim());
        if (existing != null) {
            throw new IllegalArgumentException(
                    "Офис с адрес '" + address + "' вече съществува!"
            );
        }

        // Create and populate Office entity
        Office office = new Office();
        office.setAddress(address.trim());
        office.setCompany(company);

        return repo.save(office);
    }

    /**
     * Retrieves an Office by its primary key.
     */
    public Office getOfficeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Office ID cannot be null");
        }
        return repo.findById(id);
    }

    /**
     * Returns all offices in the system.
     */
    public List<Office> getAllOffices() {
        return repo.findAll();
    }

    /**
     * Returns all offices belonging to a specific company.
     */
    public List<Office> getOfficesByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }
        return repo.findByCompanyId(companyId);
    }

    /**
     * Updates an existing Office entity.
     */
    public Office updateOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office cannot be null");
        }
        return repo.update(office);
    }

    /**
     * Deletes an Office by its ID.
     */
    public void deleteOffice(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Office ID cannot be null");
        }

        System.out.println("🗑️ Deleting office with ID: " + id);

        try {
            repo.deleteById(id);
            System.out.println("✅ Office deleted successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to delete office: " + e.getMessage());
            throw new RuntimeException(
                    "Грешка при изтриване на офиса: " + e.getMessage(),
                    e
            );
        }
    }
}

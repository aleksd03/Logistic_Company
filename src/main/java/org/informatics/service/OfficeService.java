package org.informatics.service;

import org.informatics.dao.OfficeDao;
import org.informatics.entity.Company;
import org.informatics.entity.Office;

import java.util.List;

public class OfficeService {
    private final OfficeDao repo = new OfficeDao();
    public Office createOffice(String address, Company company) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Office address cannot be empty");
        }
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        Office office = new Office();
        office.setAddress(address.trim());
        office.setCompany(company);
        return repo.save(office);
    }

    public Office getOfficeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Office ID cannot be null");
        }
        return repo.findById(id);
    }

    public List<Office> getAllOffices() {
        return repo.findAll();
    }

    public List<Office> getOfficesByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }
        return repo.findByCompanyId(companyId);
    }

    public Office updateOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office cannot be null");
        }
        return repo.update(office);
    }

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
            throw new RuntimeException("Грешка при изтриване на офиса: " + e.getMessage(), e);
        }
    }
}
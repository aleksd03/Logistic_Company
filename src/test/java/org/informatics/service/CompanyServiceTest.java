package org.informatics.service;

import org.informatics.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceTest {

    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyService = new CompanyService();
    }

    // 1️⃣ Create company successfully
    @Test
    void createCompany_shouldCreateCompanySuccessfully() {
        Company company = companyService.createCompany("ALVAS Logistics");

        assertNotNull(company);
        assertEquals("ALVAS Logistics", company.getName());
        assertNotNull(company.getId());
    }

    // 2️⃣ Validation: null name
    @Test
    void createCompany_shouldThrowException_whenNameIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.createCompany(null)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    // 3️⃣ Validation: empty name
    @Test
    void createCompany_shouldThrowException_whenNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.createCompany("   ")
        );

        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    // 4️⃣ Get company by ID
    @Test
    void getCompanyById_shouldReturnCompany_whenExists() {
        Company company = companyService.createCompany("Test Company");

        Company found = companyService.getCompanyById(company.getId());

        assertNotNull(found);
        assertEquals(company.getId(), found.getId());
    }

    // 5️⃣ Get company by invalid ID
    @Test
    void getCompanyById_shouldReturnNull_whenNotExists() {
        Company found = companyService.getCompanyById(999L);

        assertNull(found);
    }

    // 6️⃣ Get all companies
    @Test
    void getAllCompanies_shouldReturnList() {
        companyService.createCompany("Company A");
        companyService.createCompany("Company B");

        assertEquals(2, companyService.getAllCompanies().size());
    }

    // 7️⃣ Delete company
    @Test
    void deleteCompany_shouldRemoveCompany() {
        Company company = companyService.createCompany("Delete Me");

        companyService.deleteCompany(company.getId());

        assertNull(companyService.getCompanyById(company.getId()));
    }
}

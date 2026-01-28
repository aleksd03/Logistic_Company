package org.informatics.service;

import org.informatics.entity.Employee;
import org.informatics.entity.User;
import org.informatics.entity.enums.EmployeeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private User user;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();

        user = new User();
        user.setId(1L);
        user.setEmail("employee@test.com");
        user.setFirstName("Petar");
        user.setLastName("Petrov");
    }

    // 1️⃣ Create employee successfully
    @Test
    void createEmployee_shouldCreateEmployeeSuccessfully() {
        Employee employee = employeeService.createEmployee(user, EmployeeType.COURIER);

        assertNotNull(employee);
        assertEquals(user, employee.getUser());
        assertEquals(EmployeeType.COURIER, employee.getEmployeeType());
    }

    // 2️⃣ Validation: null user
    @Test
    void createEmployee_shouldThrowException_whenUserIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.createEmployee(null, EmployeeType.COURIER)
        );

        assertTrue(ex.getMessage().contains("User"));
    }

    // 3️⃣ Validation: null employee type
    @Test
    void createEmployee_shouldThrowException_whenEmployeeTypeIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.createEmployee(user, null)
        );

        assertTrue(ex.getMessage().contains("type"));
    }

    // 4️⃣ Fetch employee by ID
    @Test
    void getEmployeeById_shouldReturnEmployee_whenExists() {
        Employee employee = employeeService.createEmployee(user, EmployeeType.OFFICE_EMPLOYEE);

        Employee found = employeeService.getEmployeeById(employee.getId());

        assertNotNull(found);
        assertEquals(employee.getId(), found.getId());
    }

    // 5️⃣ Fetch non-existing employee
    @Test
    void getEmployeeById_shouldReturnNull_whenNotExists() {
        Employee found = employeeService.getEmployeeById(999L);

        assertNull(found);
    }

    // 6️⃣ Fetch all employees
    @Test
    void getAllEmployees_shouldReturnList() {
        employeeService.createEmployee(user, EmployeeType.COURIER);

        assertFalse(employeeService.getAllEmployees().isEmpty());
    }
}

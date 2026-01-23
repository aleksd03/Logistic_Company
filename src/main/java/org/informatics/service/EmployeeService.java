package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.entity.Employee;
import org.informatics.entity.Company;
import org.informatics.entity.Office;
import org.informatics.entity.User;

public class EmployeeService {
    private final EmployeeDao repo = new EmployeeDao();

    public void createForUser(User user, Company company, Office office) {
        Employee e = new Employee();
        e.setUser(user);
        e.setCompany(company);
        e.setOffice(office);
        repo.save(e);
    }
}

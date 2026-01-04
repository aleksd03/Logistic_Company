package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.entity.Employee;
import org.informatics.entity.Company;
import org.informatics.entity.User;

public class EmployeeService {

    private final EmployeeDao repo = new EmployeeDao();

    public void createForUser(User user, Company company) {
        Employee e = new Employee();
        e.setUser(user);
        e.setCompany(company);
        repo.save(e);
    }
}

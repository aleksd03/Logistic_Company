package org.informatics.service;

import org.informatics.dao.EmployeeDao;
import org.informatics.logistic.entity.Employee;
import org.informatics.logistic.entity.LogisticCompany;
import org.informatics.entity.User;

public class EmployeeService {

    private final EmployeeDao repo = new EmployeeDao();

    public void createForUser(User user, LogisticCompany company) {
        Employee e = new Employee();
        e.setUser(user);
        e.setCompany(company);
        repo.save(e);
    }
}

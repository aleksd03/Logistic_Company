package org.informatics.service;

import org.informatics.dao.ClientDao;
import org.informatics.entity.Company;
import org.informatics.entity.Client;
import org.informatics.entity.User;

public class ClientService {
    private final ClientDao repo = new ClientDao();

    public void createForUser(User user, Company company) {
        Client c = new Client();
        c.setUser(user);
        c.setCompany(company);
        repo.save(c);
    }
}

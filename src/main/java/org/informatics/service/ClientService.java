package org.informatics.service;

import org.informatics.dao.ClientDao;
import org.informatics.entity.Client;
import org.informatics.entity.Company;
import org.informatics.entity.User;

import java.util.List;

public class ClientService {
    private final ClientDao repo = new ClientDao();

    public Client createForUser(User user, Company company) {
        Client client = new Client();
        client.setUser(user);
        client.setCompany(company);
        return repo.save(client);
    }

    public Client createClient(User user, Company company) {
        return createForUser(user, company);
    }

    public Client getClientById(Long id) {
        return repo.findById(id);
    }

    public List<Client> getAllClients() {
        return repo.findAll();
    }

    public Client getClientByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public Client updateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        System.out.println("📝 Updating client: " + client.getId());
        return repo.update(client);
    }

    public void deleteClient(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }

        System.out.println("🗑️ Deleting client with ID: " + id);

        try {
            repo.deleteById(id);
            System.out.println("✅ Client deleted successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to delete client: " + e.getMessage());
            throw new RuntimeException("Грешка при изтриване на клиента: " + e.getMessage(), e);
        }
    }
}
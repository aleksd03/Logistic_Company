package org.informatics.service;

import org.informatics.entity.Client;
import org.informatics.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    private ClientService clientService;

    private User user;

    @BeforeEach
    void setUp() {
        clientService = new ClientService();

        user = new User();
        user.setId(1L);
        user.setEmail("client@test.com");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
    }

    // 1️⃣ Register client successfully
    @Test
    void createClient_shouldCreateClientSuccessfully() {
        Client client = clientService.createClient(user);

        assertNotNull(client);
        assertEquals(user, client.getUser());
    }

    // 2️⃣ Validation: null user
    @Test
    void createClient_shouldThrowException_whenUserIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientService.createClient(null)
        );

        assertTrue(ex.getMessage().contains("User"));
    }

    // 3️⃣ Fetch client by ID
    @Test
    void getClientById_shouldReturnClient_whenExists() {
        Client client = clientService.createClient(user);

        Client found = clientService.getClientById(client.getId());

        assertNotNull(found);
        assertEquals(client.getId(), found.getId());
    }

    // 4️⃣ Fetch client by non-existing ID
    @Test
    void getClientById_shouldReturnNull_whenClientDoesNotExist() {
        Client found = clientService.getClientById(999L);

        assertNull(found);
    }

    // 5️⃣ Fetch all clients
    @Test
    void getAllClients_shouldReturnList() {
        clientService.createClient(user);

        assertFalse(clientService.getAllClients().isEmpty());
    }
}

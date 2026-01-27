package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Client;
import org.informatics.entity.Shipment;
import org.informatics.entity.enums.Role;
import org.informatics.service.ClientService;
import org.informatics.service.ShipmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/client-shipments")
public class ClientShipmentsServlet extends HttpServlet {

    private final ShipmentService shipmentService = new ShipmentService();
    private final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userRole") != Role.CLIENT) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            Long userId = (Long) session.getAttribute("userId");

            // Намери клиента
            Client client = clientService.getClientByUserId(userId);

            if (client == null) {
                throw new RuntimeException("Client not found for user ID: " + userId);
            }

            // Вземи всички пратки където клиентът е подател ИЛИ получател
            List<Shipment> sentShipments = shipmentService.getShipmentsBySender(client.getId());
            List<Shipment> receivedShipments = shipmentService.getShipmentsByReceiver(client.getId());

            List<Shipment> allShipments = new ArrayList<>();
            allShipments.addAll(sentShipments);
            allShipments.addAll(receivedShipments);

            List<Shipment> uniqueShipments = allShipments.stream()
                    .distinct()
                    .sorted((s1, s2) -> s2.getRegistrationDate().compareTo(s1.getRegistrationDate()))
                    .toList();

            request.setAttribute("shipments", uniqueShipments);
            request.setAttribute("clientId", client.getId());

            System.out.println("Client " + client.getId() + " has " + uniqueShipments.size() + " shipments");

            request.getRequestDispatcher("/WEB-INF/views/client-shipments.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR loading client shipments: " + e.getMessage());
            request.setAttribute("error", "Грешка при зареждане на пратки: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
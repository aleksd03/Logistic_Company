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

/**
 * Servlet responsible for displaying all shipments
 * associated with a logged-in client (sent or received).
 */
@WebServlet("/client-shipments")
public class ClientShipmentsServlet extends HttpServlet {

    // Services used to access shipment and client data
    private final ShipmentService shipmentService = new ShipmentService();
    private final ClientService clientService = new ClientService();

    /**
     * Handles GET requests for the client's shipments page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve existing session (do not create a new one)
        HttpSession session = request.getSession(false);

        // Allow access only to authenticated CLIENT users
        if (session == null || session.getAttribute("userRole") != Role.CLIENT) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            // Extract logged-in user's ID from session
            Long userId = (Long) session.getAttribute("userId");

            // Find the Client entity associated with the user
            Client client = clientService.getClientByUserId(userId);

            if (client == null) {
                throw new RuntimeException("Client not found for user ID: " + userId);
            }

            // Retrieve shipments where the client is sender
            List<Shipment> sentShipments =
                    shipmentService.getShipmentsBySender(client.getId());

            // Retrieve shipments where the client is receiver
            List<Shipment> receivedShipments =
                    shipmentService.getShipmentsByReceiver(client.getId());

            // Combine sent and received shipments
            List<Shipment> allShipments = new ArrayList<>();
            allShipments.addAll(sentShipments);
            allShipments.addAll(receivedShipments);

            // Remove duplicates and sort by registration date (newest first)
            List<Shipment> uniqueShipments = allShipments.stream()
                    .distinct()
                    .sorted((s1, s2) ->
                            s2.getRegistrationDate().compareTo(s1.getRegistrationDate()))
                    .toList();

            // Pass data to the JSP view
            request.setAttribute("shipments", uniqueShipments);
            request.setAttribute("clientId", client.getId());

            System.out.println(
                    "Client " + client.getId() +
                            " has " + uniqueShipments.size() + " shipments"
            );

            // Forward request to the client shipments view
            request.getRequestDispatcher(
                    "/WEB-INF/views/client-shipments.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            // Handle and log errors
            e.printStackTrace();
            System.err.println("ERROR loading client shipments: " + e.getMessage());

            // Forward to error page with message
            request.setAttribute(
                    "error",
                    "Грешка при зареждане на пратки: " + e.getMessage()
            );
            request.getRequestDispatcher(
                    "/WEB-INF/views/error.jsp"
            ).forward(request, response);
        }
    }
}

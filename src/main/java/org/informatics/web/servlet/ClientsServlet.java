package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Client;
import org.informatics.entity.Company;
import org.informatics.entity.enums.Role;
import org.informatics.service.ClientService;
import org.informatics.service.CompanyService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet responsible for managing clients.
 * Allows EMPLOYEE users to view, update, and delete clients.
 */
@WebServlet("/clients")
public class ClientsServlet extends HttpServlet {

    // Services used for client and company operations
    private final ClientService clientService = new ClientService();
    private final CompanyService companyService = new CompanyService();

    /**
     * Handles GET requests:
     * - Displays the clients page
     * - Processes client deletion when action=delete
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve existing session (do not create a new one)
        HttpSession session = request.getSession(false);

        // Allow access only to EMPLOYEE users
        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Check if a specific action is requested
        String action = request.getParameter("action");

        // Handle client deletion
        if ("delete".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                System.out.println("🗑️ Attempting to delete client with ID: " + id);

                clientService.deleteClient(id);

                // Redirect with success message
                String successMsg = java.net.URLEncoder.encode(
                        "Клиентът е изтрит успешно!",
                        "UTF-8"
                );
                response.sendRedirect(
                        request.getContextPath() + "/clients?success=" + successMsg
                );
                return;

            } catch (Exception e) {
                // Handle deletion errors
                e.printStackTrace();
                System.err.println("❌ Error deleting client: " + e.getMessage());

                String errorMsg = java.net.URLEncoder.encode(
                        "Грешка при изтриване: " + e.getMessage(),
                        "UTF-8"
                );
                response.sendRedirect(
                        request.getContextPath() + "/clients?error=" + errorMsg
                );
                return;
            }
        }

        // Default GET behavior: load and display clients
        try {
            List<Client> clients = clientService.getAllClients();
            List<Company> companies = companyService.getAllCompanies();

            // Pass data to the JSP view
            request.setAttribute("clients", clients);
            request.setAttribute("companies", companies);

            // Forward to clients view
            request.getRequestDispatcher(
                    "/WEB-INF/views/clients.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            // Handle loading errors
            e.printStackTrace();
            request.setAttribute(
                    "error",
                    "Грешка при зареждане на клиенти: " + e.getMessage()
            );
            request.getRequestDispatcher(
                    "/WEB-INF/views/error.jsp"
            ).forward(request, response);
        }
    }

    /**
     * Handles POST requests:
     * - Updates a client's associated company
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve existing session (do not create a new one)
        HttpSession session = request.getSession(false);

        // Allow access only to EMPLOYEE users
        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            // Extract request parameters
            String idParam = request.getParameter("id");
            String companyIdParam = request.getParameter("companyId");

            // Update client only if ID is provided
            if (idParam != null && !idParam.isEmpty()) {
                Long clientId = Long.parseLong(idParam);
                Client client = clientService.getClientById(clientId);

                if (client != null) {
                    // Assign or remove company association
                    if (companyIdParam != null && !companyIdParam.isEmpty()) {
                        Long companyId = Long.parseLong(companyIdParam);
                        Company company = companyService.getCompanyById(companyId);
                        client.setCompany(company);
                    } else {
                        client.setCompany(null);
                    }

                    // Persist client changes
                    clientService.updateClient(client);
                    System.out.println("✅ Client updated: " + clientId);
                }
            }

            // Redirect with success message
            String successMsg = java.net.URLEncoder.encode(
                    "Клиентът е актуализиран успешно!",
                    "UTF-8"
            );
            response.sendRedirect(
                    request.getContextPath() + "/clients?success=" + successMsg
            );

        } catch (Exception e) {
            // Handle update errors
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode(
                    "Грешка: " + e.getMessage(),
                    "UTF-8"
            );
            response.sendRedirect(
                    request.getContextPath() + "/clients?error=" + errorMsg
            );
        }
    }
}

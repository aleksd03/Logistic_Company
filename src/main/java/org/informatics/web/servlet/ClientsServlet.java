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

@WebServlet("/clients")
public class ClientsServlet extends HttpServlet {
    private final ClientService clientService = new ClientService();
    private final CompanyService companyService = new CompanyService();  // ← ДОБАВИ ТОВА!

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                System.out.println("🗑️ Attempting to delete client with ID: " + id);

                clientService.deleteClient(id);

                String successMsg = java.net.URLEncoder.encode("Клиентът е изтрит успешно!", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/clients?success=" + successMsg);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ Error deleting client: " + e.getMessage());
                String errorMsg = java.net.URLEncoder.encode("Грешка при изтриване: " + e.getMessage(), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/clients?error=" + errorMsg);
                return;
            }
        }

        try {
            List<Client> clients = clientService.getAllClients();
            List<Company> companies = companyService.getAllCompanies();

            request.setAttribute("clients", clients);
            request.setAttribute("companies", companies);

            request.getRequestDispatcher("/WEB-INF/views/clients.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Грешка при зареждане на клиенти: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            String idParam = request.getParameter("id");
            String companyIdParam = request.getParameter("companyId");

            if (idParam != null && !idParam.isEmpty()) {
                Long clientId = Long.parseLong(idParam);
                Client client = clientService.getClientById(clientId);

                if (client != null) {
                    if (companyIdParam != null && !companyIdParam.isEmpty()) {
                        Long companyId = Long.parseLong(companyIdParam);
                        Company company = companyService.getCompanyById(companyId);
                        client.setCompany(company);
                    } else {
                        client.setCompany(null);
                    }

                    clientService.updateClient(client);
                    System.out.println("✅ Client updated: " + clientId);
                }
            }

            String successMsg = java.net.URLEncoder.encode("Клиентът е актуализиран успешно!", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/clients?success=" + successMsg);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode("Грешка: " + e.getMessage(), "UTF-8");
            response.sendRedirect(request.getContextPath() + "/clients?error=" + errorMsg);
        }
    }
}
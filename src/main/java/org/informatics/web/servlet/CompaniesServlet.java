package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Company;
import org.informatics.entity.enums.Role;
import org.informatics.service.CompanyService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet responsible for managing companies.
 * Allows EMPLOYEE users to view, create, update, and delete companies.
 */
@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    // Service responsible for company-related operations
    private final CompanyService companyService = new CompanyService();

    /**
     * Handles GET requests:
     * - Displays the companies list
     * - Processes delete and edit actions
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

        // Check for optional action parameter
        String action = request.getParameter("action");

        // Handle company deletion
        if ("delete".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                companyService.deleteCompany(id);

                // Redirect with success message
                String successMsg = java.net.URLEncoder.encode(
                        "Компанията е изтрита успешно!",
                        "UTF-8"
                );
                response.sendRedirect(
                        request.getContextPath() + "/companies?success=" + successMsg
                );
                return;

            } catch (Exception e) {
                // Handle deletion errors
                e.printStackTrace();
                String errorMsg = java.net.URLEncoder.encode(
                        "Грешка при изтриване: " + e.getMessage(),
                        "UTF-8"
                );
                response.sendRedirect(
                        request.getContextPath() + "/companies?error=" + errorMsg
                );
                return;
            }
        }

        // Handle edit action (preload company data)
        if ("edit".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Company company = companyService.getCompanyById(id);
                request.setAttribute("editCompany", company);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Default GET behavior: load and display companies
        try {
            List<Company> companies = companyService.getAllCompanies();
            request.setAttribute("companies", companies);

            // Forward to companies view
            request.getRequestDispatcher(
                    "/WEB-INF/views/companies.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            // Handle loading errors
            e.printStackTrace();
            request.setAttribute(
                    "error",
                    "Грешка при зареждане на компании: " + e.getMessage()
            );
            request.getRequestDispatcher(
                    "/WEB-INF/views/error.jsp"
            ).forward(request, response);
        }
    }

    /**
     * Handles POST requests:
     * - Creates a new company
     * - Updates an existing company
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
            String name = request.getParameter("name");

            // Update existing company
            if (idParam != null && !idParam.isEmpty()) {
                Long id = Long.parseLong(idParam);
                Company company = companyService.getCompanyById(id);
                if (company != null) {
                    company.setName(name);
                    companyService.updateCompany(company);
                }
            }
            // Create new company
            else {
                companyService.createCompany(name);
            }

            // Redirect back to companies list
            response.sendRedirect(request.getContextPath() + "/companies");

        } catch (Exception e) {
            // Handle create/update errors
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode(
                    e.getMessage(),
                    "UTF-8"
            );
            response.sendRedirect(
                    request.getContextPath() + "/companies?error=" + errorMsg
            );
        }
    }
}

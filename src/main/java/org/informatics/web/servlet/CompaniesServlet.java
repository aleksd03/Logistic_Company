package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Company;
import org.informatics.entity.enums.Role;
import org.informatics.service.CompanyService;

import java.io.IOException;
import java.util.List;

@WebServlet("/companies")
public class CompaniesServlet extends HttpServlet {

    private final CompanyService companyService = new CompanyService();

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
                companyService.deleteCompany(id);

                String successMsg = java.net.URLEncoder.encode("Компанията е изтрита успешно!", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/companies?success=" + successMsg);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = java.net.URLEncoder.encode("Грешка при изтриване: " + e.getMessage(), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/companies?error=" + errorMsg);
                return;
            }
        }

        if ("edit".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Company company = companyService.getCompanyById(id);
                request.setAttribute("editCompany", company);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            List<Company> companies = companyService.getAllCompanies();
            request.setAttribute("companies", companies);
            request.getRequestDispatcher("/WEB-INF/views/companies.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Грешка при зареждане на компании: " + e.getMessage());
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
            String name = request.getParameter("name");

            if (idParam != null && !idParam.isEmpty()) {
                Long id = Long.parseLong(idParam);
                Company company = companyService.getCompanyById(id);
                if (company != null) {
                    company.setName(name);
                    companyService.updateCompany(company);
                }
            } else {
                companyService.createCompany(name);
            }

            response.sendRedirect(request.getContextPath() + "/companies");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect(request.getContextPath() + "/companies?error=" + errorMsg);
        }
    }
}
package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.informatics.entity.Company;
import org.informatics.entity.Office;
import org.informatics.entity.enums.Role;
import org.informatics.service.CompanyService;
import org.informatics.service.OfficeService;

import java.io.IOException;
import java.util.List;

@WebServlet("/offices")
public class OfficesServlet extends HttpServlet {
    private final OfficeService officeService = new OfficeService();
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
                System.out.println("🗑️ Attempting to delete office with ID: " + id);

                officeService.deleteOffice(id);

                String successMsg = java.net.URLEncoder.encode("Офисът е изтрит успешно!", "UTF-8");
                response.sendRedirect(request.getContextPath() + "/offices?success=" + successMsg);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ Error deleting office: " + e.getMessage());
                String errorMsg = java.net.URLEncoder.encode("Грешка при изтриване: " + e.getMessage(), "UTF-8");
                response.sendRedirect(request.getContextPath() + "/offices?error=" + errorMsg);
                return;
            }
        }

        if ("edit".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Office office = officeService.getOfficeById(id);
                request.setAttribute("editOffice", office);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            List<Office> offices = officeService.getAllOffices();
            List<Company> companies = companyService.getAllCompanies();

            request.setAttribute("offices", offices);
            request.setAttribute("companies", companies);

            request.getRequestDispatcher("/WEB-INF/views/offices.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Грешка при зареждане на офиси: " + e.getMessage());
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
            String address = request.getParameter("address");
            Long companyId = Long.parseLong(request.getParameter("companyId"));

            Company company = companyService.getCompanyById(companyId);

            if (idParam != null && !idParam.isEmpty()) {
                Long id = Long.parseLong(idParam);
                Office office = officeService.getOfficeById(id);
                if (office != null) {
                    office.setAddress(address);
                    office.setCompany(company);
                    officeService.updateOffice(office);
                }
            } else {
                officeService.createOffice(address, company);
            }

            response.sendRedirect(request.getContextPath() + "/offices");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
            response.sendRedirect(request.getContextPath() + "/offices?error=" + errorMsg);
        }
    }
}
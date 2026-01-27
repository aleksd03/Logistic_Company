package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.informatics.entity.Company;
import org.informatics.entity.Employee;
import org.informatics.entity.Office;
import org.informatics.entity.enums.Role;
import org.informatics.service.CompanyService;
import org.informatics.service.EmployeeService;
import org.informatics.service.OfficeService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();
    private final CompanyService companyService = new CompanyService();
    private final OfficeService officeService = new OfficeService();

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
                System.out.println("🗑️ Attempting to delete employee with ID: " + id);

                employeeService.deleteEmployee(id);

                String successMsg = java.net.URLEncoder.encode("Служителят е изтрит успешно!", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/employees?success=" + successMsg);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("❌ Error deleting employee: " + e.getMessage());
                String errorMsg = java.net.URLEncoder.encode("Грешка при изтриване: " + e.getMessage(), StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/employees?error=" + errorMsg);
                return;
            }
        }

        try {
                List<Employee> employees = employeeService.getAllEmployees();
            List<Company> companies = companyService.getAllCompanies();
            List<Office> offices = officeService.getAllOffices();

            request.setAttribute("employees", employees);
            request.setAttribute("companies", companies);
            request.setAttribute("offices", offices);

            request.getRequestDispatcher("/WEB-INF/views/employees.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Грешка при зареждане на служители: " + e.getMessage());
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
            String officeIdParam = request.getParameter("officeId");

            if (idParam != null && !idParam.isEmpty()) {
                // UPDATE EMPLOYEE
                Long employeeId = Long.parseLong(idParam);
                Employee employee = employeeService.getEmployeeById(employeeId);

                if (employee != null) {
                    // Update company
                    if (companyIdParam != null && !companyIdParam.isEmpty()) {
                        Long companyId = Long.parseLong(companyIdParam);
                        Company company = companyService.getCompanyById(companyId);
                        employee.setCompany(company);
                    } else {
                        employee.setCompany(null);
                    }

                    // Update office
                    if (officeIdParam != null && !officeIdParam.isEmpty()) {
                        Long officeId = Long.parseLong(officeIdParam);
                        Office office = officeService.getOfficeById(officeId);
                        employee.setOffice(office);
                    } else {
                        employee.setOffice(null);
                    }

                    employeeService.updateEmployee(employee);
                    System.out.println("✅ Employee updated: " + employeeId);
                }
            }

            String successMsg = java.net.URLEncoder.encode("Служителят е актуализиран успешно!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/employees?success=" + successMsg);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = java.net.URLEncoder.encode("Грешка: " + e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/employees?error=" + errorMsg);
        }
        }
    }
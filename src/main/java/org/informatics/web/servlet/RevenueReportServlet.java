package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Shipment;
import org.informatics.entity.enums.Role;
import org.informatics.service.ShipmentService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generates a revenue report for a selected date range.
 * Accessible only by EMPLOYEE users.
 */
@WebServlet("/revenue-report")
public class RevenueReportServlet extends HttpServlet {

    // Service responsible for shipment and revenue calculations
    private final ShipmentService shipmentService = new ShipmentService();

    /**
     * Handles GET requests:
     * Calculates revenue and lists shipments within a given date range.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve existing session
        HttpSession session = request.getSession(false);

        // Access control: only EMPLOYEE users are allowed
        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Read date parameters from request
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        Double totalRevenue = null;
        List<Shipment> shipments = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Proceed only if both dates are provided
        if (startDateStr != null && !startDateStr.isEmpty() &&
                endDateStr != null && !endDateStr.isEmpty()) {

            try {
                // Parse input dates (HTML date input format)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = LocalDate.parse(startDateStr, formatter);
                endDate = LocalDate.parse(endDateStr, formatter);

                // Validate date order
                if (startDate.isAfter(endDate)) {
                    request.setAttribute(
                            "error",
                            "Началната дата не може да е след крайната дата!"
                    );
                } else {
                    // Convert to full-day LocalDateTime range
                    LocalDateTime startDateTime = startDate.atStartOfDay();
                    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

                    // Calculate total revenue for the period
                    totalRevenue = shipmentService
                            .calculateRevenueForPeriod(startDateTime, endDateTime);

                    // Retrieve all shipments for the period
                    shipments = shipmentService
                            .getShipmentsForPeriod(startDateTime, endDateTime);

                    // Set result attributes
                    request.setAttribute("totalRevenue", totalRevenue);
                    request.setAttribute("shipments", shipments);
                }

            } catch (Exception e) {
                // Handle parsing or calculation errors
                e.printStackTrace();
                request.setAttribute(
                        "error",
                        "Грешка при изчисляване на приходите: " + e.getMessage()
                );
            }
        }

        // Preserve input values for form re-rendering
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);

        // Forward to revenue report JSP
        request.getRequestDispatcher("/WEB-INF/views/revenue-report.jsp")
               .forward(request, response);
    }
}


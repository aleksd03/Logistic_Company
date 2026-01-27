package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Shipment;
import org.informatics.entity.enums.Role;
import org.informatics.service.ShipmentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee-shipments")
public class EmployeeShipmentsServlet extends HttpServlet {
    private final ShipmentService shipmentService = new ShipmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userRole") != Role.EMPLOYEE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            List<Shipment> shipments = shipmentService.getAllShipments();
            request.setAttribute("shipments", shipments);

            System.out.println("Loaded " + shipments.size() + " shipments");

            request.getRequestDispatcher("/WEB-INF/views/employee-shipments.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR loading shipments: " + e.getMessage());
            request.setAttribute("error", "Грешка при зареждане на пратки: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}

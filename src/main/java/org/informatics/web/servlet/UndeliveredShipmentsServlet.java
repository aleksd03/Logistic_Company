package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Shipment;
import org.informatics.entity.enums.Role;
import org.informatics.service.ShipmentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/undelivered-shipments")
public class UndeliveredShipmentsServlet extends HttpServlet {

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
            List<Shipment> undeliveredShipments = shipmentService.getUndeliveredShipments();

            request.setAttribute("shipments", undeliveredShipments);
            request.getRequestDispatcher("/WEB-INF/views/undelivered-shipments.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Грешка при зареждане на неполучени пратки: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}

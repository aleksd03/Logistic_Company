package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.Role;
import org.informatics.service.AuthService;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final AuthService auth = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String first = request.getParameter("firstName");
        String last  = request.getParameter("lastName");
        String email = request.getParameter("email");
        String pass  = request.getParameter("password");
        String conf  = request.getParameter("confirm");
        String role  = request.getParameter("role");

        if (pass == null || !pass.equals(conf)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            auth.register(first, last, email, pass, Role.valueOf(role));
            request.setAttribute("success", "Account created. Please sign in.");
        } catch (IllegalArgumentException exception) {
            request.setAttribute("error", exception.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
}
package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.informatics.entity.User;
import org.informatics.service.AuthService;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet responsible for user authentication (login).
 * Handles rendering the login page and processing login submissions.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Service responsible for authentication logic
    private final AuthService auth = new AuthService();

    /**
     * Handles GET requests:
     * Displays the login page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forward request to login JSP
        request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"
        ).forward(request, response);
    }

    /**
     * Handles POST requests:
     * Processes user login and initializes session data.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract login credentials from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Email: " + email);

        // Attempt authentication
        Optional<User> u = auth.login(email, password);

        // Login failed
        if (u.isEmpty()) {
            System.out.println("Login failed - user not found or wrong password");

            // Return to login page with error message
            request.setAttribute(
                    "error",
                    "Невалиден имейл или парола!"
            );
            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
            ).forward(request, response);
            return;
        }

        // Login successful
        User user = u.get();
        System.out.println("User found: " + user.getEmail());
        System.out.println("User role: " + user.getRole());

        // Create a new session
        HttpSession session = request.getSession(true);
        System.out.println("Session ID: " + session.getId());

        // Store user information in session
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userRole", user.getRole());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());

        // Debug session contents
        System.out.println("Session userId: " + session.getAttribute("userId"));
        System.out.println("Session userEmail: " + session.getAttribute("userEmail"));
        System.out.println("Session userRole: " + session.getAttribute("userRole"));
        System.out.println("Session firstName: " + session.getAttribute("firstName"));
        System.out.println("Session lastName: " + session.getAttribute("lastName"));
        System.out.println("===================");

        // Redirect to home page after successful login
        response.sendRedirect(request.getContextPath() + "/");
    }
}


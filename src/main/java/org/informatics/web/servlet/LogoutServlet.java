package org.informatics.web.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet responsible for logging out the currently authenticated user.
 * Invalidates the session and redirects to the home page.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Handles POST requests:
     * Logs the user out by invalidating the current session.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Retrieve existing session without creating a new one
        HttpSession session = request.getSession(false);

        // Invalidate session if it exists
        if (session != null) {
            session.invalidate();
        }

        // Redirect to home page after logout
        response.sendRedirect(request.getContextPath() + "/");
    }

    /**
     * Handles GET requests:
     * Delegates to POST for consistent logout behavior.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Treat GET logout requests the same as POST
        doPost(request, response);
    }
}



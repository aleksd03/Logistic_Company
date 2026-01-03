package org.informatics.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.informatics.service.AuthService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService auth = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String pass  = request.getParameter("password");

        auth.login(email, pass).ifPresentOrElse(user -> {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("email",  user.getEmail());
            session.setAttribute("role",   user.getRole().name());

            try {
                response.sendRedirect(request.getContextPath() + "/");
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }, () -> {
            try {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}


package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.security.AuthenticationService;
import mate.academy.internetshop.web.filter.AuthenticationFilter;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);
    private AuthenticationService authenticationService
            = (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User user = authenticationService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("user_id", user.getId());
        } catch (AuthenticationException e) {
            LOGGER.warn("Authentication failed for user :" + login + " Stack trace: " + e.getMessage());
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}

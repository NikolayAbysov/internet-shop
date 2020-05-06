package mate.academy.internetshop.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/allAdmin", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/allAdmin/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order", List.of(Role.RoleName.USER));
        protectedUrls.put("/order/new", List.of(Role.RoleName.USER));
        protectedUrls.put("/order/delete", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/all", List.of(Role.RoleName.USER));
        protectedUrls.put("/shop", List.of(Role.RoleName.USER));
        protectedUrls.put("/shop/cart", List.of(Role.RoleName.USER));
        protectedUrls.put("/shop/cart/delete", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUrl = req.getServletPath();

        if (protectedUrls.get(reqUrl) == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute("user_id");
        if (userId == null || userService.get(userId) == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(reqUrl))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/service/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}

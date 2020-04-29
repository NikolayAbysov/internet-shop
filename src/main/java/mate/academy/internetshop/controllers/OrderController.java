package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class OrderController extends HttpServlet {
    private static Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService
            = (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userService.get(USER_ID);
        Order order;
        List<Order> userOrders;
        if (req.getParameter("userId") != null) {
            ShoppingCart shoppingCart
                    = shoppingCartService.getByUserId(Long.valueOf(req.getParameter("userId")));
            order = orderService.completeOrder(shoppingCart.getProducts(), user);
            req.setAttribute("products", order.getProducts());
            req.getRequestDispatcher("/WEB-INF/views/orders/order.jsp").forward(req, resp);
        } else if (req.getParameter("orderId") != null) {
            order = orderService.get(Long.valueOf(req.getParameter("orderId")));
            req.setAttribute("products", order.getProducts());
            req.getRequestDispatcher("/WEB-INF/views/orders/order.jsp").forward(req, resp);
        } else if (req.getParameter("get") != null && req.getParameter("get").equals("all")) {
            userOrders = orderService.getUserOrders(user);
            req.setAttribute("userOrders", userOrders);
            req.getRequestDispatcher("/WEB-INF/views/orders/allOrders.jsp").forward(req, resp);
        } else if (req.getParameter("remove") != null) {
            orderService.delete(Long.valueOf(req.getParameter("remove")));
            userOrders = orderService.getUserOrders(user);
            req.setAttribute("userOrders", userOrders);
            req.getRequestDispatcher("/WEB-INF/views/orders/allOrders.jsp").forward(req, resp);
        }
    }
}

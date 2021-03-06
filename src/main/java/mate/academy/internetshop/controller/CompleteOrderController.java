package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
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
        ShoppingCart shoppingCart
                = shoppingCartService.getByUserId((Long) req.getSession().getAttribute("user_id"));
        Order order = orderService.completeOrder(shoppingCart.getProducts(),
                userService.get(shoppingCart.getUserId()));
        req.setAttribute("products", order.getProducts());
        req.getRequestDispatcher("/WEB-INF/views/orders/order.jsp").forward(req, resp);
    }
}

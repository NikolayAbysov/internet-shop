package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;

public class ShoppingCartController extends HttpServlet {
    private static Long USER_ID = 1L;

    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        List<Product> products = shoppingCartService.getAllProducts(shoppingCart);
        req.setAttribute("products", products);
        req.setAttribute("userName", shoppingCartService.getByUserId(USER_ID).getUser().getLogin());
        req.getRequestDispatcher("/WEB-INF/views/shoppingCart/shoppingCart.jsp").forward(req, resp);
    }
}


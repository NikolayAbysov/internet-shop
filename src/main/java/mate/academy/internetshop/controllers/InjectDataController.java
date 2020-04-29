package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService
            = (UserService) INJECTOR.getInstance(UserService.class);
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User shion = new User("Shion");
        User benio = new User("Benio");
        userService.create(shion);
        userService.create(benio);

        Product hack = new Product("Hack", 12.5);
        Product slash = new Product("Slash", 13.0);
        productService.create(hack);
        productService.create(slash);

        ShoppingCart shoppingCartShion = new ShoppingCart(shion);
        ShoppingCart shoppingCartBenio = new ShoppingCart(benio);
        shoppingCartService.create(shoppingCartShion);
        shoppingCartService.create(shoppingCartBenio);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}

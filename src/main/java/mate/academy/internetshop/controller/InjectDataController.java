package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;
import mate.academy.internetshop.util.InitDataBaseUtil;

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
        InitDataBaseUtil.init();

        byte[] salt1 = HashUtil.getSalt();
        byte[] salt2 = HashUtil.getSalt();
        byte[] salt3 = HashUtil.getSalt();

        User shion = new User("Shion",
                HashUtil.hashPassword("123", salt1), salt1, Set.of(Role.of("USER")));
        User benio = new User("Benio",
                HashUtil.hashPassword("123", salt2), salt2, Set.of(Role.of("USER")));
        User admin = new User("admin",
                HashUtil.hashPassword("admin", salt3), salt3, Set.of(Role.of("ADMIN")));
        userService.create(shion);
        userService.create(benio);
        userService.create(admin);

        Product hack = new Product("Hack", 12.5);
        Product slash = new Product("Slash", 13.0);
        productService.create(hack);
        productService.create(slash);

        ShoppingCart shoppingCartShion = new ShoppingCart(shion.getId());
        ShoppingCart shoppingCartBenio = new ShoppingCart(benio.getId());
        shoppingCartService.create(shoppingCartShion);
        shoppingCartService.create(shoppingCartBenio);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}

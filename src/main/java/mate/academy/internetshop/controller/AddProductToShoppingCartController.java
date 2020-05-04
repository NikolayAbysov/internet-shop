package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        shoppingCartService.addProduct(shoppingCartService
                .getByUserId((Long) req.getSession().getAttribute("user_id")),
                productService.get(Long.valueOf(req.getParameter("id"))));
        resp.sendRedirect(req.getContextPath() + "products?get=all");
    }
}

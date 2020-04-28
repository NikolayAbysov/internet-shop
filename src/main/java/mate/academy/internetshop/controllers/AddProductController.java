package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class AddProductController extends HttpServlet {

    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    private ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productName = req.getParameter("productName");
        String productPrice = req.getParameter("productPrice");

        if (productName.length() > 0 && productPrice.length() > 0) {
            productService.create(new Product(productName, Double.valueOf(productPrice)));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Please, fill all fields!");
            req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp").forward(req, resp);
        }
    }
}
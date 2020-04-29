package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class ProductsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productService.getAll();
        req.setAttribute("products", products);

        if (req.getParameter("get") != null && req.getParameter("get").equals("all")) {
            req.getRequestDispatcher("/WEB-INF/views/products/all.jsp").forward(req, resp);
        } else if (req.getParameter("get") != null && req.getParameter("get").equals("allAdmin")) {
            req.getRequestDispatcher("/WEB-INF/views/products/allAdmin.jsp").forward(req, resp);
        } else if (req.getParameter("remove") != null
                && req.getParameter("remove").equals("true")) {
            productService.delete(Long.valueOf(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/products?get=allAdmin");
        }
    }
}

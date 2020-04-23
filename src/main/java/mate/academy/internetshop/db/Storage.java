package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Product;

public class Storage {
    private static Long productId = 0L;
    public static final List<Product> products = new ArrayList<>();

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }
}

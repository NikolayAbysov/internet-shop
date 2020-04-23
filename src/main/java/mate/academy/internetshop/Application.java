package mate.academy.internetshop;

import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {

        Product product = new Product("Item");
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        productService.create(product);
        System.out.println(productService.get(product.getId()));

        product.setName("Item2");
        productService.update(product);
        System.out.println(productService.get(product.getId()));
        System.out.println(Storage.products);

        productService.delete(product.getId());
        System.out.println(Storage.products);
    }
}

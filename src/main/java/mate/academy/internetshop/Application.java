package mate.academy.internetshop;

import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

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

        User user = new User("Shion");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        System.out.println(userService.create(user));

        user.setName("Benio");
        userService.update(user);
        System.out.println(userService.get(user.getId()));
        System.out.println(userService.delete(user.getId()));

        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    }
}

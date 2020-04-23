package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Product;
import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    List<Product> getAllAvailable();

    Product update(Product product);

    boolean delete(Long id);
}

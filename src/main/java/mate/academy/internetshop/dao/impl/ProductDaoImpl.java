package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.anno.Dao;
import mate.academy.internetshop.model.Product;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        product.setId(Math.abs(new Random().nextLong()));
        Storage.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.ofNullable(Storage.products
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id: " + id)));
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        Storage.products.set(Storage.products.indexOf(product), product);
        return product;
    }

    @Override
    public void delete(Long id) {
        Storage.products.removeIf(i -> i.getId().equals(id));
    }
}

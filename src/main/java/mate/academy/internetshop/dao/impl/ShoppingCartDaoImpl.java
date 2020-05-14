package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.set(Storage.shoppingCarts.indexOf(shoppingCart), shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(s -> s.getId().equals(id));
    }
}

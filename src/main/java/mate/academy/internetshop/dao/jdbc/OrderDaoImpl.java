package mate.academy.internetshop.dao.jdbc;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.model.Order;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order element) {
        return null;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}

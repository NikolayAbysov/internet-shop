package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User shoppingCart);

    Optional<User> get(Long id);

    User update(User shoppingCart);

    boolean delete(Long id);
}

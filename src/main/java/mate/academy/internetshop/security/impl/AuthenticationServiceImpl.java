package mate.academy.internetshop.security.impl;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.anno.Inject;
import mate.academy.internetshop.lib.anno.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.security.AuthenticationService;
import mate.academy.internetshop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.getByLogin(login).orElseThrow(()
                -> new AuthenticationException("Incorrect login or password"));
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}

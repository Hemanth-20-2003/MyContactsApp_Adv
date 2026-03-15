package com.mycontact.auth.strategy;

import com.mycontact.auth.Authentication;
import com.mycontact.user.model.User;
import com.mycontact.user.service.UserService;
import java.util.Map;
import java.util.Optional;

/**
 * Basic Authentication strategy using email and password.
 * Implements password hashing for security.
 */
public class BasicAuth implements Authentication {
    private Map<String, User> userDatabase;

    /**
     * Constructor for BasicAuth.
     * @param userDatabase the map of registered users
     */
    public BasicAuth(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        User user = userDatabase.get(email);
        if (user != null) {
            try {
                String hashedInput = UserService.hashPassword(password);
                if (user.getPasswordHash().equals(hashedInput)) {
                    return Optional.of(user);
                }
            } catch (Exception e) {
                // Handle hashing exception
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
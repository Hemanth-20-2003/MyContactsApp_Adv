package com.mycontact.auth;

import com.mycontact.user.model.User;
import java.util.Optional;

/**
 * Authentication interface for user login.
 * Uses Strategy Pattern to allow different authentication methods.
 */
public interface Authentication {
    /**
     * Authenticates a user with email and password.
     * @param email the user's email
     * @param password the user's password
     * @return Optional containing the user if authentication succeeds, empty otherwise
     */
    Optional<User> authenticate(String email, String password);
}
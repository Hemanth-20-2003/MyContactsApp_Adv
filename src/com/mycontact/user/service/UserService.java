package com.mycontact.user.service;

import com.mycontact.user.builder.UserBuilder;
import com.mycontact.user.factory.UserFactory;
import com.mycontact.user.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Service class handling user registration and authentication operations.
 * Coordinates the use of Builder and Factory patterns to create validated User objects.
 * Includes password hashing and input validation.
 */
public class UserService {
    /**
     * Registers a new user with the specified type, name, email, and password.
     * @param type the user type (FREE or PREMIUM)
     * @param name the user's name
     * @param email the user's email
     * @param password the user's password
     * @return the created User object
     * @throws Exception if validation fails or user creation fails
     */
    public static User registerUser(String type, String name, String email, String password) throws Exception {
        UserBuilder builder = new UserBuilder()
            .setName(name)
            .setEmail(email)
            .setPassword(password);

        return UserFactory.createUser(type, builder);
    }

    /**
     * Hashes a password using SHA-256.
     * @param password the plain text password
     * @return the hashed password as a hexadecimal string
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

/**
 * Builder class for constructing User objects.
 * Implements the Builder Pattern to facilitate step-by-step construction
 * of User instances with validation and password hashing.
 */
package com.mycontact.user.builder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mycontact.user.model.User;

public class UserBuilder {
    private String email;
    private String password;
    private String name;

    public UserBuilder setEmail(String email) throws IllegalArgumentException {
        if (!User.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) throws IllegalArgumentException {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.password = password;
        return this;
    }

    public UserBuilder setName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() throws NoSuchAlgorithmException {
        return hashPassword(password);
    }

    public String getName() {
        return name;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
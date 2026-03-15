/**
 * Abstract base class representing a User in the My Contacts App.
 * Demonstrates encapsulation with private fields and validation logic.
 * Uses regex for email validation and provides getters/setters with validation.
 */
package com.mycontact.user.model;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import com.mycontact.user.builder.UserBuilder;

public abstract class User {
    private String email;
    private String passwordHash;
    private String name;

    // Regex pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    protected User(UserBuilder builder) throws IllegalArgumentException, NoSuchAlgorithmException {
        this.email = builder.getEmail();
        this.passwordHash = builder.getPasswordHash();
        this.name = builder.getName();
    }

    // Email validation using regex
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Getters and setters with validation
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) throws IllegalArgumentException {
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
}
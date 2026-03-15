/**
 * Abstract base class representing a User in the My Contacts App.
 * Demonstrates encapsulation with private fields and validation logic.
 * Uses regex for email validation and provides getters/setters with validation.
 *
 * OOP Concepts: Encapsulation, validation logic
 * Java Concepts: Regular expressions for email validation, exception handling
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

    /**
     * Protected constructor using UserBuilder for object construction.
     * @param builder the UserBuilder containing user data
     * @throws IllegalArgumentException if validation fails
     * @throws NoSuchAlgorithmException if password hashing fails
     */
    protected User(UserBuilder builder) throws IllegalArgumentException, NoSuchAlgorithmException {
        this.email = builder.getEmail();
        this.passwordHash = builder.getPasswordHash();
        this.name = builder.getName();
    }

    /**
     * Validates email using regex pattern.
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Getters and setters with validation

    /**
     * Gets the user's email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email with validation.
     * @param email the email to set
     * @throws IllegalArgumentException if email is invalid
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    /**
     * Gets the user's password hash.
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the user's password hash with validation.
     * @param passwordHash the password hash to set
     * @throws IllegalArgumentException if password hash is invalid
     */
    public void setPasswordHash(String passwordHash) throws IllegalArgumentException {
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        this.passwordHash = passwordHash;
    }

    /**
     * Gets the user's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name with validation.
     * @param name the name to set
     * @throws IllegalArgumentException if name is invalid
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
}

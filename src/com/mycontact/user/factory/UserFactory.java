/**
 * Factory class for creating different types of User objects.
 * Implements the Factory Pattern to encapsulate object creation logic
 * and return appropriate User subclasses based on the type.
 */
package com.mycontact.user.factory;

import java.security.NoSuchAlgorithmException;

import com.mycontact.user.builder.UserBuilder;
import com.mycontact.user.model.FreeUser;
import com.mycontact.user.model.PremiumUser;
import com.mycontact.user.model.User;

public class UserFactory {
    public static User createUser(String type, UserBuilder builder) throws IllegalArgumentException, NoSuchAlgorithmException {
        if ("FREE".equalsIgnoreCase(type)) {
            return new FreeUser(builder);
        } else if ("PREMIUM".equalsIgnoreCase(type)) {
            return new PremiumUser(builder);
        } else {
            throw new IllegalArgumentException("Invalid user type: " + type);
        }
    }
}
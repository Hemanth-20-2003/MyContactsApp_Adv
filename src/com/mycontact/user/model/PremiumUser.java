/**
 * Concrete implementation of User for premium users.
 * Part of the Factory Pattern for creating different user types.
 */
package com.mycontact.user.model;

import java.security.NoSuchAlgorithmException;
import com.mycontact.user.builder.UserBuilder;

public class PremiumUser extends User {
    /**
     * Constructor for PremiumUser.
     * @param builder the UserBuilder containing user data
     * @throws IllegalArgumentException if validation fails
     * @throws NoSuchAlgorithmException if password hashing fails
     */
    public PremiumUser(UserBuilder builder) throws IllegalArgumentException, NoSuchAlgorithmException {
        super(builder);
    }
}
/**
 * Represents a Free User type in the system.
 * Extends the abstract User class and is created via the Factory Pattern.
 */
package com.mycontact.user.model;

import java.security.NoSuchAlgorithmException;

import com.mycontact.user.builder.UserBuilder;

public class FreeUser extends User {
    public FreeUser(UserBuilder builder) throws IllegalArgumentException, NoSuchAlgorithmException {
        super(builder);
    }
}
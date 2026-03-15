/**
 * Service class handling user registration operations.
 * Coordinates the use of Builder and Factory patterns to create validated User objects.
 * Includes password hashing and input validation.
 */
package com.mycontact.user.service;

import com.mycontact.user.builder.UserBuilder;
import com.mycontact.user.factory.UserFactory;
import com.mycontact.user.model.User;

public class UserService {
    public static User registerUser(String type, String name, String email, String password) throws Exception {
        UserBuilder builder = new UserBuilder()
            .setName(name)
            .setEmail(email)
            .setPassword(password);

        return UserFactory.createUser(type, builder);
    }
}
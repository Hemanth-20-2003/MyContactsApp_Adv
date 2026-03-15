/**
 * =====================================================================
 * MAIN CLASS - MyContactsApp
 * =====================================================================
 * * Use Case 01: User Registration
 * * Description:
 * This class demonstrates user registration functionality by creating
 * an account with email, password, and profile information using OOP
 * concepts, design patterns, and Java features.
 * * At this stage, the application:
 * - Collects user input for name, email, password, and user type
 * - Validates input and handles exceptions
 * - Creates user objects using Factory and Builder patterns
 * - Provides feedback on registration success or failure
 * * This maps encapsulation, validation logic, password hashing,
 * Factory Pattern, Builder Pattern, input validation, exception handling,
 * and regular expressions for email validation.
 * * @author Developer
 * @version 1.0
 */

package com.main;

import java.util.Scanner;
import com.mycontact.user.model.User;
import com.mycontact.user.service.UserService;

public class MyContactsApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to My Contacts App - User Registration");

        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            System.out.print("Enter user type (FREE or PREMIUM): ");
            String type = scanner.nextLine();

            User newUser = UserService.registerUser(type, name, email, password);

            System.out.println("User registered successfully!");
            System.out.println("Name: " + newUser.getName());
            System.out.println("Email: " + newUser.getEmail());
            System.out.println("User Type: " + (newUser instanceof com.mycontact.user.model.FreeUser ? "Free" : "Premium"));

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
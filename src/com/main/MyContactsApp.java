/**
 * =====================================================================
 * MAIN CLASS - MyContactsApp
 * =====================================================================
 * * Use Case 01: User Registration
 * * Description:
 * This class demonstrates user registration functionality by creating
 * an account with email, password, and profile information using OOP
 * concepts, design patterns, and Java features.
 * * Use Case 02: User Authentication
 * * Description:
 * This class demonstrates user authentication by logging in with
 * credentials to access their contact list using polymorphism,
 * Strategy Pattern, Singleton, and Java features.
 * * At this stage, the application:
 * - Collects user input for registration and login
 * - Validates input and handles exceptions
 * - Creates user objects using Factory and Builder patterns
 * - Authenticates users using Strategy Pattern with BasicAuth
 * - Manages sessions using Singleton SessionManager
 * - Uses Optional for handling login results
 * - Provides feedback on operations
 * * This maps encapsulation, validation logic, password hashing,
 * Factory Pattern, Builder Pattern, Strategy Pattern, Singleton,
 * input validation, exception handling, regular expressions,
 * session management, and Optional for results.
 * * @author Developer
 * @version 2.0
 */

package com.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import com.mycontact.auth.Authentication;
import com.mycontact.auth.session.SessionManager;
import com.mycontact.auth.strategy.BasicAuth;
import com.mycontact.user.model.User;
import com.mycontact.user.service.UserService;

public class MyContactsApp {
    private static Map<String, User> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authentication auth = new BasicAuth(userDatabase);

        System.out.println("Welcome to My Contacts App");

        while (true) {
            if (SessionManager.getInstance().isLoggedIn()) {
                showLoggedInMenu(scanner);
            } else {
                showLoggedOutMenu(scanner, auth);
            }
        }
    }

    private static void showLoggedOutMenu(Scanner scanner, Authentication auth) {
        System.out.println("\n1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                registerUser(scanner);
                break;
            case 2:
                loginUser(scanner, auth);
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void registerUser(Scanner scanner) {
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
            userDatabase.put(newUser.getEmail(), newUser);

            System.out.println("User registered successfully!");
            System.out.println("Name: " + newUser.getName());
            System.out.println("Email: " + newUser.getEmail());
            System.out.println("User Type: " + (newUser instanceof com.mycontact.user.model.FreeUser ? "Free" : "Premium"));

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void loginUser(Scanner scanner, Authentication auth) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Optional<User> userOptional = auth.authenticate(email, password);
        if (userOptional.isPresent()) {
            SessionManager.getInstance().startSession(userOptional.get());
            System.out.println("Login successful! Welcome " + userOptional.get().getName());
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void showLoggedInMenu(Scanner scanner) {
        System.out.println("\n1. View Profile");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                SessionManager.getInstance().endSession();
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void viewProfile() {
        User user = SessionManager.getInstance().getLoggedInUser();
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("User Type: " + (user instanceof com.mycontact.user.model.FreeUser ? "Free" : "Premium"));
    }
}
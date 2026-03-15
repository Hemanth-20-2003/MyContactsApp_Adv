/**
 * =====================================================================
 * MAIN CLASS - MyContactsApp
 * =====================================================================
 * * Use Case 03: User Profile Management
 * * Description:
 * This class demonstrates user profile management by allowing logged-in
 * users to update profile information, change password, or manage preferences
 * using OOP concepts, Command Pattern, and Java features.
 * * At this stage, the application:
 * - Allows logged-in users to edit name, email, and password
 * - Uses Command Pattern for update operations with undo capability
 * - Validates input using encapsulated methods
 * - Follows JavaBeans conventions for setters
 * - Implements security best practices for password updates
 * - Provides feedback on update success or failure
 * * This maps User class with setter methods, validation encapsulated in methods,
 * Command Pattern for profile update operations, JavaBeans conventions,
 * data validation, and security best practices.
 * * @author Developer
 * @version 3.0
 */

package com.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import com.mycontact.auth.Authentication;
import com.mycontact.auth.session.SessionManager;
import com.mycontact.auth.strategy.BasicAuth;
import com.mycontact.user.command.Command;
import com.mycontact.user.command.RemoteControl;
import com.mycontact.user.command.UpdateEmailCommand;
import com.mycontact.user.command.UpdateNameCommand;
import com.mycontact.user.command.UpdatePasswordCommand;
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
        System.out.println("2. Edit Profile");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                viewProfile();
                break;
            case 2:
                editProfile(scanner);
                break;
            case 3:
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

    private static void editProfile(Scanner scanner) {
        User user = SessionManager.getInstance().getLoggedInUser();
        RemoteControl remote = new RemoteControl();

        System.out.println("\n--- Edit Profile ---");
        System.out.println("1. Edit Name");
        System.out.println("2. Edit Email");
        System.out.println("3. Change Password");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                Command nameCommand = new UpdateNameCommand(user, newName);
                remote.execute(nameCommand);
                break;
            case 2:
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                Command emailCommand = new UpdateEmailCommand(user, newEmail);
                remote.execute(emailCommand);
                break;
            case 3:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                Command passwordCommand = new UpdatePasswordCommand(user, newPassword);
                remote.execute(passwordCommand);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}
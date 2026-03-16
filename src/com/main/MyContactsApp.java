/**
 * =====================================================================
 * MAIN CLASS - MyContactsApp
 * =====================================================================
 * * Use Case 05: View Contact Details
 * * Use Case 06: Edit Contact
 * * Description:
 * This class demonstrates viewing and editing contact details for logged-in
 * users using OOP concepts, design patterns, and Java features.
 * * At this stage, the application:
 * - Allows logged-in users to select and view detailed contact information
 * - Uses Decorator Pattern for display formatting
 * - Uses immutable ContactView objects for read-only access
 * - Allows users to edit contact data with undo support
 * - Uses Command Pattern and Memento Pattern for state preservation
 * - Validates input before updating contact state
 * * This maps Getter methods, toString() override for display formatting,
 * Decorator Pattern, String formatting, Optional for nullable fields,
 * immutable view objects, Command Pattern, and Memento Pattern.
 * * @author Developer
 * @version 6.0
 */

package com.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import com.mycontact.auth.Authentication;
import com.mycontact.auth.session.SessionManager;
import com.mycontact.auth.strategy.BasicAuth;
import com.mycontact.contact.decorator.DetailedContactDisplay;
import com.mycontact.contact.factory.ContactFactory;
import com.mycontact.contact.model.Contact;
import com.mycontact.contact.model.ContactView;
import com.mycontact.contact.model.Email;
import com.mycontact.contact.model.PhoneNumber;
import com.mycontact.contact.command.UpdateContactNameCommand;
import com.mycontact.user.command.Command;
import com.mycontact.user.command.RemoteControl;
import com.mycontact.user.command.UpdateEmailCommand;
import com.mycontact.user.command.UpdateNameCommand;
import com.mycontact.user.command.UpdatePasswordCommand;
import com.mycontact.user.model.User;
import com.mycontact.user.service.UserService;
import java.util.List;

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
        System.out.println("3. Manage Contacts");
        System.out.println("4. Logout");
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
                manageContacts(scanner);
                break;
            case 4:
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

    private static void manageContacts(Scanner scanner) {
        User user = SessionManager.getInstance().getLoggedInUser();

        System.out.println("\n--- Manage Contacts ---");
        System.out.println("1. View All Contacts");
        System.out.println("2. View Contact Details");
        System.out.println("3. Add Contact");
        System.out.println("4. Edit Contact");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                user.viewContacts();
                break;
            case 2:
                viewContactDetails(scanner, user);
                break;
            case 3:
                addContact(scanner, user);
                break;
            case 4:
                editContact(scanner, user);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void viewContactDetails(Scanner scanner, User user) {
        List<Contact> contacts = user.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("Select a contact to view details:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName());
        }
        System.out.print("Enter contact number: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (index >= 0 && index < contacts.size()) {
            Contact selectedContact = contacts.get(index);
            ContactView contactView = new ContactView(selectedContact);

            // Show a safe, read-only snapshot of the contact
            System.out.println("\n--- Contact Snapshot ---");
            System.out.println("Name: " + contactView.getName());
            System.out.println("ID: " + contactView.getId());

            // Use Decorator for detailed display formatting
            DetailedContactDisplay display = new DetailedContactDisplay(selectedContact);
            System.out.println(display.display());
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private static void editContact(Scanner scanner, User user) {
        List<Contact> contacts = user.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("Select a contact to edit:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName());
        }
        System.out.print("Enter contact number: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }

        Contact selected = contacts.get(index);
        RemoteControl remote = new RemoteControl();

        System.out.println("\n--- Edit Contact ---");
        System.out.println("1. Change Name");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            System.out.print("Enter new contact name: ");
            String newName = scanner.nextLine();
            UpdateContactNameCommand nameCommand = new UpdateContactNameCommand(selected, newName);
            remote.execute(nameCommand);

            System.out.print("Undo this change? (y/n): ");
            String undo = scanner.nextLine();
            if (undo.equalsIgnoreCase("y")) {
                remote.undo(nameCommand);
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void addContact(Scanner scanner, User user) {
        try {
            System.out.print("Enter contact name: ");
            String name = scanner.nextLine();

            System.out.print("Enter contact type (PERSON or ORGANIZATION): ");
            String type = scanner.nextLine();

            Contact contact = ContactFactory.createContact(type, name);

            // Add phone numbers
            System.out.print("How many phone numbers to add? ");
            int phoneCount = scanner.nextInt();
            scanner.nextLine(); // consume newline
            for (int i = 0; i < phoneCount; i++) {
                System.out.print("Enter phone number " + (i + 1) + ": ");
                String phone = scanner.nextLine();
                System.out.print("Enter label (e.g., Home, Work): ");
                String label = scanner.nextLine();
                PhoneNumber phoneNumber = new PhoneNumber(phone, label);
                contact.addPhoneNumber(phoneNumber);
            }

            // Add emails
            System.out.print("How many emails to add? ");
            int emailCount = scanner.nextInt();
            scanner.nextLine(); // consume newline
            for (int i = 0; i < emailCount; i++) {
                System.out.print("Enter email " + (i + 1) + ": ");
                String email = scanner.nextLine();
                System.out.print("Enter label (e.g., Personal, Work): ");
                String label = scanner.nextLine();
                Email emailObj = new Email(email, label);
                contact.addEmail(emailObj);
            }

            user.addContact(contact);
            System.out.println("Contact added successfully!");

        } catch (Exception e) {
            System.out.println("Failed to add contact: " + e.getMessage());
        }
    }
}
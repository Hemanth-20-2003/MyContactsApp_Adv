/**
 * =====================================================================
 * MAIN CLASS - MyContactsApp
 * =====================================================================
 * * Use Case 09:  Search Contacts

 * * Description:
 * This class demonstrates viewing, editing, deleting, and performing bulk
 * operations on contact details for logged-in users using OOP concepts,
 * design patterns, and Java features.
 * * At this stage, the application:
 * - Allows logged-in users to select and view detailed contact information
 * - Uses Decorator Pattern for display formatting
 * - Uses immutable ContactView objects for read-only access
 * - Allows users to edit contact data with undo support
 * - Uses Command Pattern and Memento Pattern for state preservation
 * - Allows users to delete contacts with confirmation and notification
 * - Supports bulk operations (batch delete, export) using streams and predicates
 * - Validates input before updating or deleting contact state
 * * This maps Getter methods, toString() override for display formatting,
 * Decorator Pattern, String formatting, Optional for nullable fields,
 * immutable view objects, Command Pattern, Memento Pattern, Observer Pattern,
 * and Streams API for bulk operations.
 * * @author Developer
 * @version 9.0
 */

package com.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.mycontact.auth.Authentication;
import com.mycontact.auth.session.SessionManager;
import com.mycontact.auth.strategy.BasicAuth;
import com.mycontact.contact.decorator.DetailedContactDisplay;
import com.mycontact.contact.factory.ContactFactory;
import com.mycontact.contact.model.Contact;
import com.mycontact.contact.model.ContactView;
import com.mycontact.contact.model.Email;
import com.mycontact.contact.model.PhoneNumber;
import com.mycontact.contact.observer.ContactDeletionObserver;
import com.mycontact.contact.search.ContactSearchService;
import com.mycontact.contact.search.EmailCriteria;
import com.mycontact.contact.search.NameCriteria;
import com.mycontact.contact.search.PhoneCriteria;
import com.mycontact.contact.search.SearchCriteria;
import com.mycontact.contact.search.TagCriteria;
import com.mycontact.contact.command.UpdateContactNameCommand;
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
        System.out.println("5. Delete Contact");
        System.out.println("6. Bulk Operations");
        System.out.println("7. Search Contacts");
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
            case 5:
                deleteContact(scanner, user);
                break;
            case 6:
                bulkOperations(scanner, user);
                break;
            case 7:
                searchContacts(scanner, user);
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

    private static void deleteContact(Scanner scanner, User user) {
        List<Contact> contacts = user.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("Select a contact to delete:");
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

        Contact toDelete = contacts.get(index);
        System.out.print("Are you sure you want to delete '" + toDelete.getName() + "'? (y/n): ");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("y")) {
            System.out.println("Delete canceled.");
            return;
        }

        // Register a simple observer to notify other components about deletion.
        user.addDeletionObserver(new ContactDeletionObserver() {
            @Override
            public void onContactDeleted(Contact contact) {
                System.out.println("Observer: Contact '" + contact.getName() + "' was deleted.");
            }
        });

        user.removeContact(toDelete);
        System.out.println("Contact deleted successfully.");
    }

    private static void bulkOperations(Scanner scanner, User user) {
        List<Contact> contacts = user.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Bulk Operations ---");
        System.out.println("1. Delete multiple contacts");
        System.out.println("2. Tag multiple contacts");
        System.out.println("3. Export contacts");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                bulkDelete(scanner, user, contacts);
                break;
            case 2:
                bulkTag(scanner, user, contacts);
                break;
            case 3:
                bulkExport(scanner, user, contacts);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void searchContacts(Scanner scanner, User user) {
        List<Contact> contacts = user.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Search Contacts ---");
        System.out.println("Search by:");
        System.out.println("1. Name");
        System.out.println("2. Phone");
        System.out.println("3. Email");
        System.out.println("4. Tag");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        SearchCriteria criteria;
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        switch (choice) {
            case 1:
                criteria = new NameCriteria(query);
                break;
            case 2:
                criteria = new PhoneCriteria(query);
                break;
            case 3:
                criteria = new EmailCriteria(query);
                break;
            case 4:
                criteria = new TagCriteria(query);
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        List<Contact> results = ContactSearchService.search(contacts, criteria);
        if (results.isEmpty()) {
            System.out.println("No contacts matched your search.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(c -> System.out.println(c.getName() + " (" + c.getId() + ")"));
        }
    }

    private static void bulkDelete(Scanner scanner, User user, List<Contact> contacts) {
        System.out.println("Enter contact numbers to delete (comma separated):");
        String input = scanner.nextLine();
        List<Contact> toDelete = parseContactSelection(input, contacts);

        if (toDelete.isEmpty()) {
            System.out.println("No valid contacts selected.");
            return;
        }

        System.out.print("Confirm delete " + toDelete.size() + " contact(s)? (y/n): ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Bulk delete cancelled.");
            return;
        }

        user.removeContacts(toDelete);
        System.out.println("Deleted " + toDelete.size() + " contacts.");
    }

    private static void bulkTag(Scanner scanner, User user, List<Contact> contacts) {
        System.out.println("Enter contact numbers to tag (comma separated):");
        String input = scanner.nextLine();
        List<Contact> toTag = parseContactSelection(input, contacts);

        if (toTag.isEmpty()) {
            System.out.println("No valid contacts selected.");
            return;
        }

        System.out.print("Enter tag to apply: ");
        String tag = scanner.nextLine();
        user.tagContacts(toTag, tag);
        System.out.println("Tagged " + toTag.size() + " contact(s) with '" + tag + "'.");
    }

    private static void bulkExport(Scanner scanner, User user, List<Contact> contacts) {
        System.out.println("Enter contact numbers to export (comma separated) or press Enter for all:");
        String input = scanner.nextLine();
        List<Contact> toExport = input.trim().isEmpty() ? contacts : parseContactSelection(input, contacts);

        String exportText = user.exportContacts(toExport);
        System.out.println("\n--- Export Output ---\n" + exportText);

        System.out.print("Save export to file? (y/n): ");
        String save = scanner.nextLine();
        if (save.equalsIgnoreCase("y")) {
            String fileName = "contacts_export_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            try {
                Files.writeString(Path.of(fileName), exportText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Export saved to " + fileName);
            } catch (IOException e) {
                System.out.println("Failed to save export: " + e.getMessage());
            }
        }
    }

    private static List<Contact> parseContactSelection(String input, List<Contact> contacts) {
        return java.util.Arrays.stream(input.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(s -> {
                try {
                    int idx = Integer.parseInt(s) - 1;
                    return (idx >= 0 && idx < contacts.size()) ? contacts.get(idx) : null;
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());
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

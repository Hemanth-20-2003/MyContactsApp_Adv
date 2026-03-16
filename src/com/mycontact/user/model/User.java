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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.mycontact.contact.model.Contact;
import com.mycontact.contact.observer.ContactDeletionObserver;
import com.mycontact.contact.tag.Tag;
import com.mycontact.user.builder.UserBuilder;

public abstract class User {
    private String email;
    private String passwordHash;
    private String name;
    private List<Contact> contacts;
    private List<ContactDeletionObserver> deletionObservers;
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
        this.contacts = new ArrayList<>();
        this.deletionObservers = new ArrayList<>();
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

    /**
     * Gets the list of contacts.
     * @return a defensive copy of the contacts list
     */
    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    /**
     * Adds a contact to the user's contact list.
     * @param contact the contact to add
     */
    public void addContact(Contact contact) {
        if (contact != null) {
            this.contacts.add(contact);
        }
    }

    /**
     * Removes a contact from the user's contact list.
     * Notifies registered observers about the deletion.
     * @param contact the contact to remove
     */
    public void removeContact(Contact contact) {
        if (this.contacts.remove(contact)) {
            notifyDeletionObservers(contact);
        }
    }

    /**
     * Registers an observer to be notified when a contact is deleted.
     * @param observer the observer to register
     */
    public void addDeletionObserver(ContactDeletionObserver observer) {
        if (observer != null) {
            this.deletionObservers.add(observer);
        }
    }

    /**
     * Removes a previously registered deletion observer.
     * @param observer the observer to remove
     */
    public void removeDeletionObserver(ContactDeletionObserver observer) {
        this.deletionObservers.remove(observer);
    }

    private void notifyDeletionObservers(Contact contact) {
        for (ContactDeletionObserver observer : deletionObservers) {
            observer.onContactDeleted(contact);
        }
    }

    /**
     * Displays all contacts.
     */
    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n\t Your Contacts -----");
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.println((i + 1) + ". Name: " + c.getName());
            System.out.println("   Phones: " + c.getPhoneNumbers());
            System.out.println("   Emails: " + c.getEmails());
            System.out.println("   Tags: " + c.getTags());
            System.out.println();
        }
    }

    /**
     * Removes multiple contacts in bulk.
     * @param contactsToRemove list of contacts to remove
     */
    public void removeContacts(List<Contact> contactsToRemove) {
        this.contacts.removeAll(contactsToRemove);
        // Notify observers for each removed contact
        contactsToRemove.forEach(this::notifyDeletionObservers);
    }

    /**
     * Tags a set of contacts with a given label.
     * @param contactsToTag list of contacts to tag
     * @param tag the tag to apply
     */
    public void tagContacts(List<Contact> contactsToTag, String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return;
        }
        Tag tagObj = Tag.of(tag);
        contactsToTag.forEach(contact -> contact.addTag(tagObj));
    }

    /**
     * Exports the provided contacts to a plain-text representation.
     * @param contactsToExport contacts to export
     * @return a formatted string representation
     */
    public String exportContacts(List<Contact> contactsToExport) {
        StringBuilder sb = new StringBuilder();
        contactsToExport.stream().forEach(contact -> {
            sb.append(contact.toString()).append("\n---\n");
        });
        return sb.toString();
    }
}


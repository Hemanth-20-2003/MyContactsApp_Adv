package com.mycontact.contact.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Abstract base class for contacts.
 * Uses composition for phone numbers and emails.
 * Includes unique ID and timestamp.
 */
public abstract class Contact {
    private UUID id;
    private String name;
    private List<PhoneNumber> phoneNumbers;
    private List<Email> emails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Contact(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be null or empty");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be null or empty");
        }
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return new ArrayList<>(phoneNumbers); // Defensive copy
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
        this.updatedAt = LocalDateTime.now();
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.remove(phoneNumber);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Email> getEmails() {
        return new ArrayList<>(emails); // Defensive copy
    }

    public void addEmail(Email email) {
        this.emails.add(email);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeEmail(Email email) {
        this.emails.remove(email);
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Phones: ").append(phoneNumbers).append("\n");
        sb.append("Emails: ").append(emails).append("\n");
        sb.append("Created: ").append(createdAt).append("\n");
        sb.append("Updated: ").append(updatedAt);
        return sb.toString();
    }
}
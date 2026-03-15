package com.mycontact.contact.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Immutable view object for Contact.
 * Provides a read-only interface for contact details.
 */
public class ContactView {
    private final UUID id;
    private final String name;
    private final List<PhoneNumber> phoneNumbers;
    private final List<Email> emails;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ContactView(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.phoneNumbers = List.copyOf(contact.getPhoneNumbers()); // Immutable list
        this.emails = List.copyOf(contact.getEmails()); // Immutable list
        this.createdAt = contact.getCreatedAt();
        this.updatedAt = contact.getUpdatedAt();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
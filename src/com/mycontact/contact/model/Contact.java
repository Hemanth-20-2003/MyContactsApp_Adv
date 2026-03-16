package com.mycontact.contact.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.mycontact.contact.model.Email;
import com.mycontact.contact.model.PhoneNumber;
import com.mycontact.contact.tag.Tag;

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
    private Set<Tag> tags;
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
        this.tags = new HashSet<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Copy constructor for creating modified versions of a Contact.
     * Performs a deep copy of the contact's state.
     * @param other the contact to copy
     */
    protected Contact(Contact other) {
        this.id = other.id;
        this.name = other.name;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
        this.phoneNumbers = new ArrayList<>();
        other.phoneNumbers.forEach(phone -> this.phoneNumbers.add(new PhoneNumber(phone.getNumber(), phone.getLabel())));
        this.emails = new ArrayList<>();
        other.emails.forEach(email -> this.emails.add(new Email(email.getAddress(), email.getLabel())));
        this.tags = new HashSet<>(other.tags);
    }

    /**
     * Creates a memento representing the current state.
     * @return a memento that can be used to restore state
     */
    public Memento createMemento() {
        return new Memento(copy());
    }

    /**
     * Restores the contact state from a memento.
     * @param memento the memento to restore from
     */
    public void restore(Memento memento) {
        Contact snapshot = memento.getSnapshot();
        this.name = snapshot.name;
        this.phoneNumbers = snapshot.phoneNumbers;
        this.emails = snapshot.emails;
        this.tags = snapshot.tags;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Memento object for storing contact state.
     */
    public static class Memento {
        private final Contact snapshot;

        private Memento(Contact snapshot) {
            this.snapshot = snapshot;
        }

        private Contact getSnapshot() {
            return snapshot;
        }
    }

    /**
     * Performs a deep copy of this contact.
     * Concrete subclasses must implement this to return the correct type.
     * @return a new Contact instance with the same state
     */
    public abstract Contact copy();

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

    public Set<Tag> getTags() {
        return new HashSet<>(tags); // Defensive copy
    }

    public void addTag(Tag tag) {
        if (tag != null) {
            this.tags.add(tag);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        this.updatedAt = LocalDateTime.now();
    }

    public void clearTags() {
        this.tags.clear();
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
        sb.append("Tags: ").append(tags).append("\n");
        sb.append("Created: ").append(createdAt).append("\n");
        sb.append("Updated: ").append(updatedAt);
        return sb.toString();
    }
}
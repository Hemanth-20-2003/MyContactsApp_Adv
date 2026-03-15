package com.mycontact.contact.model;

import com.mycontact.user.model.User;

/**
 * Represents an email address for a contact.
 * Reuses email validation from User class.
 */
public class Email {
    private String address;
    private String label; // e.g., "Personal", "Work"

    public Email(String address, String label) throws IllegalArgumentException {
        if (!User.isValidEmail(address)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.address = address;
        this.label = label != null ? label : "Default";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IllegalArgumentException {
        if (!User.isValidEmail(address)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label != null ? label : "Default";
    }

    @Override
    public String toString() {
        return label + ": " + address;
    }
}
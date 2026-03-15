package com.mycontact.contact.decorator;

import com.mycontact.contact.model.Contact;

/**
 * Detailed display decorator with formatting.
 */
 public class DetailedContactDisplay extends ContactDisplayDecorator {
    public DetailedContactDisplay(Contact contact) {
        super(contact);
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("Contact Details\n");
        sb.append("================================\n");
        sb.append(String.format("Name: %-20s\n", contact.getName()));
        sb.append(String.format("ID: %-20s\n", contact.getId()));
        sb.append(String.format("Created: %-20s\n", contact.getCreatedAt()));
        sb.append(String.format("Updated: %-20s\n", contact.getUpdatedAt()));
        sb.append("Phone Numbers:\n");
        if (contact.getPhoneNumbers().isEmpty()) {
            sb.append("  (None)\n");
        } else {
            contact.getPhoneNumbers().forEach(phone -> sb.append(String.format("  - %-15s: %s\n", phone.getLabel(), phone.getNumber())));
        }
        sb.append("Emails:\n");
        if (contact.getEmails().isEmpty()) {
            sb.append("  (None)\n");
        } else {
            contact.getEmails().forEach(email -> sb.append(String.format("  - %-15s: %s\n", email.getLabel(), email.getAddress())));
        }
        sb.append("================================\n");
        return sb.toString();
    }
}

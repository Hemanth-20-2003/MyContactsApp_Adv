package com.mycontact.contact.decorator;

import com.mycontact.contact.model.Contact;

/**
 * Basic display decorator.
 */
public class BasicContactDisplay extends ContactDisplayDecorator {
    public BasicContactDisplay(Contact contact) {
        super(contact);
    }

    @Override
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(contact.getName()).append("\n");
        sb.append("ID: ").append(contact.getId()).append("\n");
        sb.append("Created: ").append(contact.getCreatedAt()).append("\n");
        sb.append("Updated: ").append(contact.getUpdatedAt()).append("\n");
        sb.append("Phone Numbers:\n");
        contact.getPhoneNumbers().forEach(phone -> sb.append("  - ").append(phone).append("\n"));
        sb.append("Emails:\n");
        contact.getEmails().forEach(email -> sb.append("  - ").append(email).append("\n"));
        return sb.toString();
    }
}

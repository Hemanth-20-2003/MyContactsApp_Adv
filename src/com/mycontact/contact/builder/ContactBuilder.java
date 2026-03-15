package com.mycontact.contact.builder;

import com.mycontact.contact.model.Contact;
import com.mycontact.contact.model.Email;
import com.mycontact.contact.model.Person;
import com.mycontact.contact.model.PhoneNumber;
import com.mycontact.contact.model.Organization;

/**
 * Builder class for constructing Contact objects.
 * Implements the Builder Pattern for flexible contact creation.
 */
public class ContactBuilder {
    private String name;
    private String type; // "PERSON" or "ORGANIZATION"

    public ContactBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ContactBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public Contact build() throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("Contact type is required");
        }

        Contact contact;
        if ("PERSON".equalsIgnoreCase(type)) {
            contact = new Person(name);
        } else if ("ORGANIZATION".equalsIgnoreCase(type)) {
            contact = new Organization(name);
        } else {
            throw new IllegalArgumentException("Invalid contact type: " + type);
        }

        return contact;
    }

    // Methods to add phones and emails after building, but since Contact is built, perhaps not needed here.
    // The builder builds the base contact, and then user can add phones/emails via methods.
}
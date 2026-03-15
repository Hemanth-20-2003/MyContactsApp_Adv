package com.mycontact.contact.factory;

import com.mycontact.contact.model.Contact;
import com.mycontact.contact.model.Organization;
import com.mycontact.contact.model.Person;

/**
 * Factory class for creating different types of Contact objects.
 * Implements the Factory Pattern to encapsulate contact creation logic.
 */
public class ContactFactory {
    /**
     * Creates a Contact object based on the specified type.
     * @param type the type of contact ("PERSON" or "ORGANIZATION")
     * @param name the name of the contact
     * @return the created Contact object
     * @throws IllegalArgumentException if type or name is invalid
     */
    public static Contact createContact(String type, String name) throws IllegalArgumentException {
        if ("PERSON".equalsIgnoreCase(type)) {
            return new Person(name);
        } else if ("ORGANIZATION".equalsIgnoreCase(type)) {
            return new Organization(name);
        } else {
            throw new IllegalArgumentException("Invalid contact type: " + type);
        }
    }
}
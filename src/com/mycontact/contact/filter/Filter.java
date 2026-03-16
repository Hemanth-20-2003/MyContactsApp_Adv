package com.mycontact.contact.filter;

import com.mycontact.contact.model.Contact;

/**
 * Filter interface for applying criteria to contacts.
 * Used for advanced filtering (composite filters, strategy).
 */
public interface Filter {
    boolean matches(Contact contact);

    default Filter and(Filter other) {
        return contact -> this.matches(contact) && other.matches(contact);
    }

    default Filter or(Filter other) {
        return contact -> this.matches(contact) || other.matches(contact);
    }
}
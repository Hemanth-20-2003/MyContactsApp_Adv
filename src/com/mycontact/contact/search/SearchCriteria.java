package com.mycontact.contact.search;

import com.mycontact.contact.model.Contact;

/**
 * Search criteria interface for matching contacts.
 * Implements the Specification Pattern for building flexible search queries.
 */
public interface SearchCriteria {
    boolean matches(Contact contact);

    default SearchCriteria and(SearchCriteria other) {
        return contact -> this.matches(contact) && other.matches(contact);
    }

    default SearchCriteria or(SearchCriteria other) {
        return contact -> this.matches(contact) || other.matches(contact);
    }
}
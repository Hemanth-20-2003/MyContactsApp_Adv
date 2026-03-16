package com.mycontact.contact.search;

import com.mycontact.contact.model.Contact;

/**
 * Search criteria that matches contact names (case-insensitive, partial match).
 */
public class NameCriteria implements SearchCriteria {
    private final String query;

    public NameCriteria(String query) {
        this.query = query == null ? "" : query.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        return contact != null && contact.getName() != null && contact.getName().toLowerCase().contains(query);
    }
}
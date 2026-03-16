package com.mycontact.contact.search;

import com.mycontact.contact.model.Contact;

/**
 * Search criteria that matches contact emails (contains, case-insensitive).
 */
public class EmailCriteria implements SearchCriteria {
    private final String query;

    public EmailCriteria(String query) {
        this.query = query == null ? "" : query.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        return contact.getEmails().stream()
            .filter(e -> e.getAddress() != null)
            .anyMatch(e -> e.getAddress().toLowerCase().contains(query));
    }
}
package com.mycontact.contact.search;

import com.mycontact.contact.model.Contact;

/**
 * Search criteria that matches contact phone numbers (contains, case-insensitive).
 */
public class PhoneCriteria implements SearchCriteria {
    private final String query;

    public PhoneCriteria(String query) {
        this.query = query == null ? "" : query.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        return contact.getPhoneNumbers().stream()
            .filter(p -> p.getNumber() != null)
            .anyMatch(p -> p.getNumber().toLowerCase().contains(query));
    }
}
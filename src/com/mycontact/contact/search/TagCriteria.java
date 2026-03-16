package com.mycontact.contact.search;

import com.mycontact.contact.model.Contact;

/**
 * Search criteria that matches contact tags (case-insensitive).
 */
public class TagCriteria implements SearchCriteria {
    private final String tag;

    public TagCriteria(String tag) {
        this.tag = tag == null ? "" : tag.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        return contact.getTags().stream()
            .filter(t -> t != null)
            .anyMatch(t -> t.toLowerCase().contains(tag));
    }
}
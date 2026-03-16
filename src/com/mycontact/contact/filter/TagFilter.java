package com.mycontact.contact.filter;

import com.mycontact.contact.model.Contact;
import com.mycontact.contact.tag.Tag;

/**
 * Filters contacts by tags (case-insensitive).
 */
public class TagFilter implements Filter {
    private final String tag;

    public TagFilter(String tag) {
        this.tag = tag == null ? "" : tag.toLowerCase();
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        return contact.getTags().stream()
            .filter(t -> t != null && t.getName() != null)
            .anyMatch(t -> t.getName().toLowerCase().contains(tag));
    }
}
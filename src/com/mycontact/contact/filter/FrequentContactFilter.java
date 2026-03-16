package com.mycontact.contact.filter;

import com.mycontact.contact.model.Contact;

/**
 * Filters contacts based on a simple "frequently contacted" heuristic.
 * Here we define frequently contacted as having multiple phone numbers.
 */
public class FrequentContactFilter implements Filter {
    private final int minPhoneNumbers;

    public FrequentContactFilter(int minPhoneNumbers) {
        this.minPhoneNumbers = Math.max(1, minPhoneNumbers);
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        return contact.getPhoneNumbers().size() >= minPhoneNumbers;
    }
}
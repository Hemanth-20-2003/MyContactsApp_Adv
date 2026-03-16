package com.mycontact.contact.filter;

import java.time.LocalDateTime;
import com.mycontact.contact.model.Contact;

/**
 * Filters contacts based on the date they were added.
 */
public class DateAddedFilter implements Filter {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public DateAddedFilter(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean matches(Contact contact) {
        if (contact == null) {
            return false;
        }
        LocalDateTime created = contact.getCreatedAt();
        if (created == null) {
            return false;
        }
        if (from != null && created.isBefore(from)) {
            return false;
        }
        if (to != null && created.isAfter(to)) {
            return false;
        }
        return true;
    }
}
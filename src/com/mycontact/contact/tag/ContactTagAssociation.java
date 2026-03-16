package com.mycontact.contact.tag;

import com.mycontact.contact.model.Contact;
import java.time.LocalDateTime;

/**
 * Association class representing the many-to-many relationship between Contact and Tag.
 * Includes the date when the tag was applied to the contact.
 */
public class ContactTagAssociation {
    private final Contact contact;
    private final Tag tag;
    private final LocalDateTime addedDate;

    public ContactTagAssociation(Contact contact, Tag tag) {
        this.contact = contact;
        this.tag = tag;
        this.addedDate = LocalDateTime.now();
    }

    public Contact getContact() {
        return contact;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactTagAssociation)) return false;
        ContactTagAssociation that = (ContactTagAssociation) o;
        return contact.equals(that.contact) && tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return contact.hashCode() + tag.hashCode();
    }

    @Override
    public String toString() {
        return tag.getName() + " (added: " + addedDate + ")";
    }
}
package com.mycontact.contact.observer;

import com.mycontact.contact.model.Contact;
import com.mycontact.contact.tag.Tag;

/**
 * Observer interface for tag changes on contacts.
 */
public interface TagChangeObserver {
    void onTagAdded(Contact contact, Tag tag);
    void onTagRemoved(Contact contact, Tag tag);
}
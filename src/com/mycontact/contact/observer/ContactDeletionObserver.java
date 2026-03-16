package com.mycontact.contact.observer;

import com.mycontact.contact.model.Contact;

/**
 * Observer interface for listening to contact deletions.
 * Implements the Observer Pattern so components can react when contacts are removed.
 */
public interface ContactDeletionObserver {
    /**
     * Called when a contact is deleted.
     * @param contact the deleted contact
     */
    void onContactDeleted(Contact contact);
}

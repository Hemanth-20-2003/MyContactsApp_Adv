package com.mycontact.contact.decorator;

/**
 * Display interface for contacts.
 * Allows different formatting strategies for contact data.
 */
public interface ContactDisplay {
    /**
     * Returns a formatted string representing the contact.
     */
    String display();
}
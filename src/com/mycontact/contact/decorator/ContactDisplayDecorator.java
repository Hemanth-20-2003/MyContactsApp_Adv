package com.mycontact.contact.decorator;

import com.mycontact.contact.model.Contact;

/**
 * Decorator Pattern for formatting contact display.
 * Provides different display formats for contacts.
 */
public abstract class ContactDisplayDecorator {
    protected Contact contact;

    public ContactDisplayDecorator(Contact contact) {
        this.contact = contact;
    }

    public abstract String display();
}




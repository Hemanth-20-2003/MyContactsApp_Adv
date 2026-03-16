package com.mycontact.contact.model;

/**
 * Represents an organization contact.
 * Extends Contact with no additional fields for simplicity.
 */
public class Organization extends Contact {
    public Organization(String name) throws IllegalArgumentException {
        super(name);
    }

    protected Organization(Organization other) {
        super(other);
    }

    @Override
    public Contact copy() {
        return new Organization(this);
    }
}
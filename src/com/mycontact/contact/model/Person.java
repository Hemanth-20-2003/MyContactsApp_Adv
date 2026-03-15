package com.mycontact.contact.model;

/**
 * Represents a person contact.
 * Extends Contact with no additional fields for simplicity.
 */
public class Person extends Contact {
    public Person(String name) throws IllegalArgumentException {
        super(name);
    }
}
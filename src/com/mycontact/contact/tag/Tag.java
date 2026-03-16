package com.mycontact.contact.tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a tag used to categorize contacts.
 * Includes validation and implements equals/hashCode for Set usage.
 * Maintains bidirectional relationship with contacts via associations.
 */
public final class Tag {
    private final String name;
    private final Set<ContactTagAssociation> associations = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public static Tag of(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        return TagFactory.getTag(name);
    }

    public String getName() {
        return name;
    }

    public Set<ContactTagAssociation> getAssociations() {
        return associations;
    }

    public void addAssociation(ContactTagAssociation association) {
        associations.add(association);
    }

    public void removeAssociation(ContactTagAssociation association) {
        associations.remove(association);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return name.equalsIgnoreCase(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}

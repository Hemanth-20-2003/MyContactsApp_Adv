package com.mycontact.contact.tag;

import java.util.Objects;

/**
 * Represents a tag used to categorize contacts.
 * Includes validation and implements equals/hashCode for Set usage.
 */
public final class Tag {
    private final String name;

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

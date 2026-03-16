package com.mycontact.contact.tag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flyweight factory for Tag instances.
 * Ensures identical tag names share the same instance.
 */
public class TagFactory {
    private static final Map<String, Tag> TAGS = new ConcurrentHashMap<>();

    public static Tag getTag(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        return TAGS.computeIfAbsent(name.trim().toLowerCase(), key -> new Tag(name.trim()));
    }
}

package com.mycontact.contact.tag;

import java.util.EnumSet;
import java.util.Set;

/**
 * EnumSet of predefined common tags.
 */
public enum PredefinedTags {
    FAMILY,
    WORK,
    FRIENDS;

    public Tag toTag() {
        return TagFactory.getTag(name());
    }

    public static Set<Tag> all() {
        return EnumSet.allOf(PredefinedTags.class).stream().map(PredefinedTags::toTag).collect(java.util.stream.Collectors.toSet());
    }
}

package com.mycontact.contact.search;

import java.util.List;
import java.util.stream.Collectors;

import com.mycontact.contact.model.Contact;

/**
 * Utility service for searching contacts using search criteria.
 */
public class ContactSearchService {
    public static List<Contact> search(List<Contact> contacts, SearchCriteria criteria) {
        if (contacts == null || criteria == null) {
            return List.of();
        }
        return contacts.stream()
            .filter(criteria::matches)
            .collect(Collectors.toList());
    }
}
package com.mycontact.contact.model;

import java.util.regex.Pattern;

/**
 * Represents a phone number for a contact.
 * Includes validation for phone number format.
 */
public class PhoneNumber {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$"); // Simple international format
    private String number;
    private String label; // e.g., "Home", "Work", "Mobile"

    public PhoneNumber(String number, String label) throws IllegalArgumentException {
        if (!isValidPhoneNumber(number)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.number = number;
        this.label = label != null ? label : "Default";
    }

    public static boolean isValidPhoneNumber(String number) {
        return number != null && PHONE_PATTERN.matcher(number).matches();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws IllegalArgumentException {
        if (!isValidPhoneNumber(number)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label != null ? label : "Default";
    }

    @Override
    public String toString() {
        return label + ": " + number;
    }
}
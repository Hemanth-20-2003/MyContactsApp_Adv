package com.mycontact.contact.command;

import com.mycontact.contact.model.Contact;
import com.mycontact.user.command.Command;

/**
 * Command for updating a contact's name.
 * Uses Memento for undo support (Memento Pattern).
 */
public class UpdateContactNameCommand implements Command {
    private final Contact contact;
    private final String newName;
    private final Contact.Memento memento;

    public UpdateContactNameCommand(Contact contact, String newName) {
        this.contact = contact;
        this.newName = newName;
        this.memento = contact.createMemento();
    }

    @Override
    public void execute() {
        try {
            contact.setName(newName);
            System.out.println("Contact name updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update contact name: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        contact.restore(memento);
        System.out.println("Contact name update undone.");
    }
}
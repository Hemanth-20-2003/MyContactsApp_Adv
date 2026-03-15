package com.mycontact.user.command;

import com.mycontact.user.model.User;

/**
 * Command for updating user's name.
 * Implements undo by storing previous value.
 */
public class UpdateNameCommand implements Command {
    private User user;
    private String newName;
    private String previousName;

    /**
     * Constructor for UpdateNameCommand.
     * @param user the user to update
     * @param newName the new name
     */
    public UpdateNameCommand(User user, String newName) {
        this.user = user;
        this.newName = newName;
        this.previousName = user.getName();
    }

    @Override
    public void execute() {
        try {
            user.setName(newName);
            System.out.println("Name updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update name: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        try {
            user.setName(previousName);
            System.out.println("Name update undone.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to undo name update: " + e.getMessage());
        }
    }
}
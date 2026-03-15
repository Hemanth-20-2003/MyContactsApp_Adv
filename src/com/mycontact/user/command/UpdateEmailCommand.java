package com.mycontact.user.command;

import com.mycontact.user.model.User;

/**
 * Command for updating user's email.
 * Implements undo by storing previous value.
 */
public class UpdateEmailCommand implements Command {
    private User user;
    private String newEmail;
    private String previousEmail;

    /**
     * Constructor for UpdateEmailCommand.
     * @param user the user to update
     * @param newEmail the new email
     */
    public UpdateEmailCommand(User user, String newEmail) {
        this.user = user;
        this.newEmail = newEmail;
        this.previousEmail = user.getEmail();
    }

    @Override
    public void execute() {
        try {
            user.setEmail(newEmail);
            System.out.println("Email updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update email: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        try {
            user.setEmail(previousEmail);
            System.out.println("Email update undone.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to undo email update: " + e.getMessage());
        }
    }
}
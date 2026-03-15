package com.mycontact.user.command;

import com.mycontact.user.model.User;
import com.mycontact.user.service.UserService;

/**
 * Command for updating user's password.
 * Implements undo by storing previous hash.
 */
public class UpdatePasswordCommand implements Command {
    private User user;
    private String newPassword;
    private String previousHash;

    /**
     * Constructor for UpdatePasswordCommand.
     * @param user the user to update
     * @param newPassword the new password
     */
    public UpdatePasswordCommand(User user, String newPassword) {
        this.user = user;
        this.newPassword = newPassword;
        this.previousHash = user.getPasswordHash();
    }

    @Override
    public void execute() {
        try {
            String newHash = UserService.hashPassword(newPassword);
            user.setPasswordHash(newHash);
            System.out.println("Password updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update password: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        user.setPasswordHash(previousHash);
        System.out.println("Password update undone.");
    }
}
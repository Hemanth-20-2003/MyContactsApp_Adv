package com.mycontact.user.command;

/**
 * Command interface for profile update operations.
 * Implements the Command Pattern to encapsulate update requests.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command if possible.
     */
    void undo();
}
package com.mycontact.user.command;

/**
 * Invoker class for executing commands.
 * Part of the Command Pattern, responsible for calling execute on commands.
 */
public class RemoteControl {
    /**
     * Executes the given command.
     * @param command the command to execute
     */
    public void execute(Command command) {
        command.execute();
    }

    /**
     * Undoes the given command.
     * @param command the command to undo
     */
    public void undo(Command command) {
        command.undo();
    }
}
package com.mycontact.auth.session;

import com.mycontact.user.model.User;

/**
 * Singleton class for managing user sessions.
 * Ensures only one session manager instance exists.
 */
public class SessionManager {
    private static SessionManager instance;
    private User loggedInUser;

    /**
     * Private constructor to prevent instantiation.
     */
    private SessionManager() {}

    /**
     * Gets the singleton instance of SessionManager.
     * @return the SessionManager instance
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Starts a session for the specified user.
     * @param user the user to log in
     */
    public void startSession(User user) {
        this.loggedInUser = user;
    }

    /**
     * Ends the current session.
     */
    public void endSession() {
        this.loggedInUser = null;
    }

    /**
     * Gets the currently logged-in user.
     * @return the logged-in user, or null if no user is logged in
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Checks if a user is currently logged in.
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
package org.example;

/**
 * Exception thrown when a group is not found in the database.
 */
public class GroupNotExistsException extends Exception {
    public GroupNotExistsException(String message) {
        super(message);
    }
}

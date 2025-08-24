package org.example;

/**
 * Exception thrown when a group exceeds the maximum allowed number of members. (10 members)
 */
public class GroupTresholdException extends Exception {
    public GroupTresholdException(String message) {
        super(message);
    }
}

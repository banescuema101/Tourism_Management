package org.example;

/**
 * Exception thrown when trying to assign a guide to a group that already has one.
 * In that case, I should first remove the existing guide and then assign a new one.
 */
public class GuideExistsException extends Exception {
    public GuideExistsException(String message) {
        super(message);
    }
}

package org.example;

/**
 * Exceptie => un grup nu a fost gasit in baza de date.
 */
public class GroupNotExistsException extends Exception {
    public GroupNotExistsException(String message) {
        super(message);
    }
}

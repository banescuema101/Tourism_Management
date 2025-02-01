package org.example;

/**
 * Exceptie => La adaugarea unui ghid la un grup, in caz ca grupul are deja un ghid.
 * Evident, normal ar fi sa il elimin si apoi sa ii reasignez unul nou.
 */
public class GuideExistsException extends Exception {
    public GuideExistsException(String message) {
        super(message);
    }
}

package org.example;

/**
 * Exceptie => de ex: la adaugarea unui membru, s-ar depasi limita maxima de 10 persoane
 * admisa.
 */
public class GroupTresholdException extends Exception {
    public GroupTresholdException(String message) {
        super(message);
    }
}

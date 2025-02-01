package org.example;

/**
 * Exceptie => daca o persoana nu exista intr-un anumit grup.
 */
public class PersonNotExistsException extends Exception{
    public PersonNotExistsException(String message){
        super(message);
    }
}

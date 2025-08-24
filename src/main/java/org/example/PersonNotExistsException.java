package org.example;

/**
 * Exception thrown when trying to access a person that does not exist in a certain group.
 */
public class PersonNotExistsException extends Exception{
    public PersonNotExistsException(String message){
        super(message);
    }
}

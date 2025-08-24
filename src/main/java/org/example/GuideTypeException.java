package org.example;

/**
 * Exception thrown when trying to assign a guide to a group, but that person
 * does not meet the required type, i.e., is not of type Professor.
 */
public class GuideTypeException extends Exception{
    public GuideTypeException(String message){
        super(message);
    }
}

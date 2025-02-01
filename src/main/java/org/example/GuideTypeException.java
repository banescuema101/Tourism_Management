package org.example;

/**
 * Exceptie => Persoana pe care vreau sa o asignez ca ghid al unui grup
 * nu respecta tipul cerut de cerinta, adica nu ar fi de tipul Professor.
 */
public class GuideTypeException extends Exception{
    public GuideTypeException(String message){
        super(message);
    }
}

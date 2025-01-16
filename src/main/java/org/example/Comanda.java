package org.example;

public abstract class Comanda {
    // throws Exception pentru a putea arunca in comenzile specifice, in metoda asta suprascrisa
    // exceptia de tip GroupNotEXistsException.
    public abstract void executa() throws Exception;
}

package org.example;

public abstract class Comanda {

    // throws Exception pentru a putea arunca in comenzile specifice, exceptii personalizate
    // pentru fiecare comanda specifica in parte, ele extinzand din start clasa Exception.
    public abstract void executa() throws Exception;
}

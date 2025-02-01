package org.example;

/**
 * Comanda de adaugare a unui muzeu in setul LinkedHasSet al bazei de date.
 */
public class ComandaAddMuseum extends Comanda{
    private Museum museum;
    public ComandaAddMuseum(Museum museum) {
        this.museum = museum;
    }
    // metoda in care pur si simpla apelez metoda de adaugare a unui muzeu in setul de muzee al bazei de date.
    @Override
    public void executa() {
        Database.Instanta().addMuseum(museum);
    }
}

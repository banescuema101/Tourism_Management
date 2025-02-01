package org.example;

/**
 * Comanda cu rol de adaugare a unui grup in baza de date.
 */
public class ComandaAddGroup extends Comanda {
    private final Group group;
    public ComandaAddGroup(Group group) {
        this.group = group;
    }
    // doar voi apela metoda din baza de date, de adaugare a unui grup.
    public void executa() {
        Database.Instanta().addGroup(group);
    }
}

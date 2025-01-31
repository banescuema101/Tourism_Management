package org.example;

public class ComandaAddGroup extends Comanda {
    private Group group;
    public ComandaAddGroup(Group group) {
        this.group = group;
    }
    // doar voi apela metoda din baza de date, de adaugare a unui grup.
    public void executa() {
        Database.Instanta().addGroup(group);
    }
}

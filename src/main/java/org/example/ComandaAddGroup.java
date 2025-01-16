package org.example;

public class ComandaAddGroup extends Comanda {
    private Group group;
    public ComandaAddGroup(Group group) {
        this.group = group;
    }
    public void executa() {
        Database.Instanta().addGroup(group);
    }

}

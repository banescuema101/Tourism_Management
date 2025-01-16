package org.example;

public class ComandaAddMuseum extends Comanda{
    private Museum museum;
    public ComandaAddMuseum(Museum museum) {
        this.museum = museum;
    }
    @Override
    public void executa() {
        Database.Instanta().addMuseum(museum);
    }
}

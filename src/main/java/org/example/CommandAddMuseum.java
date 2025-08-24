package org.example;

/**
  * Command for adding a museum to the LinkedHashSet of the database.
  */
public class CommandAddMuseum extends Command {
    private final Museum museum;
    public CommandAddMuseum(Museum museum) {
        this.museum = museum;
    }
    // Method in which I simply call the method to add a museum to the set of museums in the database.
    @Override
    public void execute() {
        Database.Instance().addMuseum(museum);
    }
}

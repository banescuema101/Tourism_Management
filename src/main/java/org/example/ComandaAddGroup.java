package org.example;

/**
 * Comanda cu rol de adaugare a unui grup in baza de date.
 */
public class ComandaAddGroup extends Comanda {
    private final Group group;
    public ComandaAddGroup(Group group) {
        this.group = group;
    }
    // voi apela metoda din baza de date, de adaugare a unui grup daca si numai daca
    // muzeul nu este supraincarcat la momentul respectiv timetable. (daca sunt deja programate
    // minim 3 grupuri => nu se mai adauga si arunc exceptie care se trateaza ulterior in Main.
    public void executa() throws MuseumFullException {
        if (Database.Instanta().disponibilitateMuzeu(group.getMuseumCode(), group.getTimetable())) {
            Database.Instanta().addGroup(group);
        } else {
            throw new MuseumFullException("Din pacate, limita maxima de grupuri pentru acest interval orar este atinsa.");
        }
    }
}

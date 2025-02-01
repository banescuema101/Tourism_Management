package org.example;

import java.io.PrintWriter;

/**
 *  Clasa de stergere a unui anume membru dintr un grup.
 */
public class ComandaRemoveMember extends Comanda {
    private final Integer museumCode;
    private final String timetable;
    private final Person person;
    private final PrintWriter pw;
    public ComandaRemoveMember(Integer museumCode, String timetable, Person person, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.person = person;
        this.pw = pw;
    }

    /**
     * Metoda elimina un membru (parametrul Person person transmis ca parametru constructorului), gasind
     * mai intai grupul din baza de date, al carui museumCode si timetable corespund cu cele date ca parametru
     * comenzii acesteia si daca il gaseste apelez metoda removeMember pe person. Cum removeMember() imi poate
     * genera o exceptie, va trebui sa o prind in aceasta metoda ( blocul catch)
     * @throws GroupNotExistsException metoda arunca exceptie in caz ca grupul nu a fost gasit in baza de date.
     */
    @Override
    public void executa() throws GroupNotExistsException {
        try {
            Group grupGasit =  Database.Instanta().findGroup(museumCode, timetable);
            if (grupGasit == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
            grupGasit.removeMember(person);
            pw.println(museumCode + " ## " + timetable + " ## removed member: " + person.toString());
        } catch (PersonNotExistsException e) {
            pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (" + person.toString() + ")");
        }
    }
}

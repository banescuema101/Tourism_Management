package org.example;

import java.io.PrintWriter;

public class ComandaRemoveGuide extends Comanda{
    private final Integer museumCode;
    private final String timetable;
    private final PrintWriter pw;
    // Aici memorez ca atribut ghidul pe care il voi gasi, pentru ca imi trebuie in Main
    // la explicitarea unei exceptii. A se vedea la operatia de REMOVE GUIDE de acolo.
    private Person ghidDeResetat;

    public Person getGhidDeResetat() {
        return ghidDeResetat;
    }

    public void setGhidDeResetat(Person ghidDeResetat) {
        this.ghidDeResetat = ghidDeResetat;
    }

    public ComandaRemoveGuide(Integer museumCode, String timetable, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.pw = pw;
    }

    /**
     * Voi gasi grupul in baza de date, apeland findGroup, daca nu il gasesc, arunc exceptie.
     * Voi apela setterul setGhidDeResetat pe grupul gasit, voi reseta ghidul grupului ( il voi sterge, il pun
     * pe null) si apoi afisez outputul actiunii in PrintWriterul corespunzator.
     *
     *
     * @throws GroupNotExistsException exceptia aruncata in caz ca grupul nu exista in baza de date.
     */
    @Override
    public void executa() throws GroupNotExistsException {
        Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
        if (grupGasit == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
        } else {
            setGhidDeResetat(grupGasit.getGuide());
            grupGasit.resetGuide();
            // afisez la removed guide: ghidul pe care il avea ghidul grupului gasit. De aceea il memorez mai
            // mai intai ca atribut in clasa ComandaRemoveGuide, pentru ca grupulGasit acum dupa resetare
            // va avea ghidul pe null.
            pw.println(museumCode + " ## " + timetable + " ## removed guide: " + ghidDeResetat.toString());
        }
    }
}

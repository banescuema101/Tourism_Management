package org.example;

import java.io.PrintWriter;
import java.util.Set;

public class ComandaRemoveGuide extends Comanda{
    private Integer museumCode;
    private String timetable;
    private PrintWriter pw;
    // aici memorez ca atribut ghidul pe care il voi gasi, pentru ca imi trebuie in main
    // la explicitarea unei exceptii. Vezi la REMOVE GUIDE.
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
    @Override
    public void executa() throws GroupNotExistsException {
        Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
        if (grupGasit == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
        } else {
            Set<Group> setGrupuri = Database.Instanta().getListaGrupuri();
//            setGrupuri.remove(grupGasit);
            setGhidDeResetat(grupGasit.getGuide());
            grupGasit.resetGuide();
//            setGrupuri.add(grupGasit);
            pw.println(museumCode + " ## " + timetable + " ## removed guide: " + ghidDeResetat.toString());
        }
    }
}

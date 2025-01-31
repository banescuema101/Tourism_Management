package org.example;

import java.io.PrintWriter;

public class ComandaFindGuide extends Comanda{
    private Integer museumCode;
    private String timetable;
    private PrintWriter pw;
    private Person persoana;
    public ComandaFindGuide(Integer museumCode, String timetable, Person persoana, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.pw = pw;
        this.persoana = persoana;
    }

    /**
     * Metoda executa() a comenzii Find Guide apeleaza metoda din baza de date, de gasire a unui
     * grup, returnand automat grupul gasit, in caz de succes. Astfel, aici voi putea sa apelez findGuide()
     * pe grupul gasit si astfel sa vad daca persoana transmisa ca parametru constructorului clasei
     * ComandaFindGuide este sau nu defapt ghid al grupului gasit.
     * @throws GroupNotExistsException Se va arunca exceptia care va fi tratata in Main, in caz ca grupul specificat
     * definit de cei doi parametrii: museumCode si timetable NU exista in baza de date.
     *
     */
    @Override
    public void executa() throws GroupNotExistsException {
        Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
        if (grupGasit == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist");
        } else {
            try {
                grupGasit.findGuide(persoana, pw);
            } catch (GuideTypeException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new guide: " + persoana);
            }
        }
    }
}

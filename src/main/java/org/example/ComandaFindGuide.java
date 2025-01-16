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
    @Override
    public void executa() throws GroupNotExistsException {
        Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
        if (grupGasit == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist");
        } else {
            Person ghid = grupGasit.getGuide();
            System.out.println("MAREEEEE:" + ghid);
            try {
                grupGasit.findGuide(persoana, pw);
            } catch (GuideTypeException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new guide: " + persoana);
            }
        }
    }
}

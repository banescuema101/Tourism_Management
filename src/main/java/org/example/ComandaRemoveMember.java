package org.example;

import java.io.PrintWriter;
import java.util.Set;

public class ComandaRemoveMember extends Comanda {
    private Integer museumCode;
    private String timetable;
    private Person person;
    private PrintWriter pw;
    public ComandaRemoveMember(Integer museumCode, String timetable, Person person, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.person = person;
        this.pw = pw;
    }
    @Override
    public void executa() throws GroupNotExistsException {
        try {
            Set<Group> grupuriBazaDate = Database.Instanta().getListaGrupuri();
            Group grupGasit =  Database.Instanta().findGroup(museumCode, timetable);
            if (grupGasit == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
//            System.out.println("Before removal: ");
//            for (Person person : grupGasit.getMembers()) {
//                System.out.println(person);
//            }
            grupGasit.removeMember(person);
//            for (Person person : grupGasit.getMembers()) {
//                System.out.println(person);
//            }
            pw.println(museumCode + " ## " + timetable + " ## removed member: " + person.toString());
        } catch (PersonNotExistsException e) {
            pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (" + person.toString() + ")");
        }
    }
}

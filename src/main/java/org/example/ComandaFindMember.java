package org.example;
import java.io.PrintWriter;

public class ComandaFindMember extends Comanda{
    private Integer museumCode;
    private String timetable;
    private PrintWriter pw;
    private Person persoana;
    public ComandaFindMember(Integer museumCode, String timetable, Person persoana, PrintWriter pw) {
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
            boolean found = false;
            for (Person member : grupGasit.getMembers()) {
                if (member.equals(persoana)) {
                    pw.println(museumCode + " ## " + timetable + " ## member found: " + persoana);
                    found = true;
                    break;
                }
            }
            if (!found) {
                pw.println(museumCode + " ## " + timetable + " ## member not exists: " + persoana);
            }
        }
    }
}

package org.example;
import java.io.PrintWriter;
import java.util.Set;

public class ComandaAddMember extends Comanda{
    private Integer museumCode;
    private String timetable;
    private Person member;
    private PrintWriter pw;
    public ComandaAddMember(Integer museumCode, String timetable, Person member, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.member = member;
        this.pw = pw;
    }
    @Override
    public void executa() throws GroupNotExistsException {
            Set<Group> grupuriBazaDeDate = Database.Instanta().getListaGrupuri();
            Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
            if (grupGasit == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
            try {
                grupGasit.addMember(member);
                pw.println(museumCode + " ## " + timetable + " ## new member: " + member.toString());
            } catch (GroupTresholdException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + member.toString() + ")");
            }
    }
}

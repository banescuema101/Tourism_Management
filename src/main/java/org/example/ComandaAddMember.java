package org.example;
import java.io.PrintWriter;

/**
 * Comanda de adaugare a unui membru la un grup specific.
 */
public class ComandaAddMember extends Comanda{
    private final Integer museumCode;
    private final String timetable;
    private final Person member;
    private final PrintWriter pw;
    public ComandaAddMember(Integer museumCode, String timetable, Person member, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.member = member;
        this.pw = pw;
    }
    @Override
    public void executa() throws GroupNotExistsException {
            //reperez grupul in baza de date.
            Group grupGasit = Database.Instanta().findGroup(museumCode, timetable);
            if (grupGasit == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
            try {
                // daca l-am gasit atunci apelez metoda prin care adaug un membru grupului
                // si afisez in fisierul de iesire outputul actiunii, sau eroare de treshold in
                // caz ca nu se depaseste limita si metoda addMember arunca exceptia aceea.
                grupGasit.addMember(member);
                pw.println(museumCode + " ## " + timetable + " ## new member: " + member.toString());
            } catch (GroupTresholdException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + member.toString() + ")");
            }
    }
}

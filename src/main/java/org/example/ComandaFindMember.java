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

    /**
     * Metoda va incerca sa gaseasca grupul dorit in baza de date, apoi
     * @throws GroupNotExistsException Se va arunca exceptia care va fi tratata in Main, in caz ca grupul specificat
     *     definit de cei doi parametrii: museumCode si timetable NU exista in baza de date.
     *     Daca l-am gasit, voi itera prin membrii grupului respectiv si daca voi gasi o persoana membru
     *     care coincide cu persoana transmisa ca parametru comenzii acesteia => afisez mesaj corespunzator
     *     in fisier.  Daca nu am gasit membrul pe care l-am cautat => afisez member not exist.
     *
     */
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

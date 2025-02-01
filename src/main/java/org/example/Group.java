package org.example;
import java.io.PrintWriter;
import java.util.*;

/**
 * Clasa care modeleaza un grup, ce are o lista de membrii, precum si un atribut ghid de
 * tip Professor, un cod de muzeu pe care il va vizita intr un anumit interval de timp,
 * adica timetable-ul de tip String.
 */
public class Group{
    private List<Person> members;
    private Professor guide;
    private Integer museumCode;
    private String timetable;

    public Group() {
        members = new ArrayList<>();
    }
    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public Professor getGuide() {
        return guide;
    }

    public void setGuide(Professor guide) {
        this.guide = guide;
    }

    public Integer getMuseumCode() {
        return museumCode;
    }

    public void setMuseumCode(Integer museumCode) {
        this.museumCode = museumCode;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    /**
     * Constructorul grupului, in care voi initializa lista de membrii al grupului precum si
     * urmatoarele campuri:
     * @param museumCode codul muzeului
     * @param timetable timetable ul pe care il voi asigna.
     */
    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<Person>();
        this.museumCode = museumCode;
        this.timetable = timetable;
    }

    /**
     * Metoda prin care adaug ghid unui grup.
     * @param guide Persoana pe care vreau sa o asignez ca ghid al grupului, voi verifica
     *              si daca este sau nu de tipul {@link Professor}
     * @throws GuideTypeException Daca nu este, comenzii de ADD GUIDE,
     *              care se va executa ulterior in aplicatie, nu ii va fi permis sa il asigneze,
     *              deci voi arunca o eroare de tipul GuideTypeException
     * @throws GuideExistsException daca grupul inca are deja un ghid asignat arunc exceptie de tipul
     *                              {@link GuideExistsException } catre respectiva comanda
     *                              {@link ComandaAddGuideToGroup}
     */
    public void addGuide(Person guide) throws GuideTypeException, GuideExistsException {
        if (!(guide instanceof Professor)) {
            throw new GuideTypeException("GuideTypeException: Guide must be a professor.");
        }
        if (this.guide != null) {
           throw new GuideExistsException("GuideExistsException: Guide already exists.");
        }
        this.guide = (Professor) guide;
    }

    /**
     * Metoda in care adaug un membru in lista de persoane a grupului.
     * @param member Membrul pe care doresc sa il adaug.
     * @throws GroupTresholdException Arunca aceasta exceptie in caz se trece de limita maxima
     * admisa in grupul de membrii (si anume: 10).
     */
    public void addMember(Person member) throws GroupTresholdException {
        if (members.size() >= 10) {
            throw new GroupTresholdException("GroupThresholdException: Group cannot have more than 10 members.");
        } else {
            members.add(member);
        }
    }

    /**
     * Metoda prin care sterg un anumit membru din lista de membrii a grupului.
     * @param member membrul pe care daca il gasesc, voi dori sa il elimin din lista grupului,
     *               ajutandu ma de un Iterator<Person>, care ma ajuta sa sterg din lista elementul
     *               gasit in iterator.
     * @throws PersonNotExistsException  arunc aceasta exceptie in caz ca nu am gasit membrul pe
     * care as fi dorit sa il elimin.
     */
    public void removeMember(Person member) throws PersonNotExistsException {
        boolean found;
        if (members.isEmpty()) {
            throw new PersonNotExistsException("PersonNotExistsException: Person was not found in the group.");
        } else {
            found = false;
            Iterator<Person> iterator = members.iterator();
            while (iterator.hasNext()) {
                Person person = iterator.next();
                if (person.equals(member)) {
                    // Daca gasesc membrul, il elimin
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new PersonNotExistsException("PersonNotExistsException: Person was not found in the group.");
            }
        }
    }

    /**
     * Metoda in care doresc sa vad daca ghidul grupului coincide cu cel transmis ca parametru
     * (ulterior, la comanda findGuide va trebui sa caut un ghid intr un anumit grup din baza
     * de date, de asta am creat aceasta metoda aici, pentru ca in clasa {@link ComandaFindGuide} sa
     * tratez doar in caz ca mi se arunca de aici exceptia de mai jos)
     * @param person Parametru de tip Person, reprezentand persoana care verific daca e sau nu ghid al grupului
     *               curent.
     * @param pw PrintWriterul in care voi afisa outputul actiunii.
     * @throws GuideTypeException exceptie, in caz ca persoana pe care vreau sa o caut
     *                            ca ghid al grupului curent nu este de tip profesor.
     */
    public void findGuide(Person person, PrintWriter pw) throws GuideTypeException {
        if (!(person instanceof Professor)) {
            throw new GuideTypeException("GuideTypeException: Guide must be a professor.");
        }
        if (this.guide != null && this.guide.equals(person)) {
            pw.println(museumCode + " ## " + timetable + " ## guide found: " + person);
        } else {
            pw.println(museumCode + " ## " + timetable + " ## guide not exists: " + person);
        }
    }

    /**
     * Metoda in care resetez ghidul unui grup.
     */
    public void resetGuide() {
        this.guide = null;
    }
}

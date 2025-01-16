package org.example;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;

public class Database {
    private static Database databaseUnica;
    private Set<Museum> museums;
    private Set<Group> groups;
    private Database() {}
    public static Database Instanta() {
        if (databaseUnica == null) {
            databaseUnica = new Database();
        }
        return databaseUnica;
    }
    public void addMuseum(Museum museum) {
        // adaugam o singura entitate muzeala.
        if (museums == null) {
            museums = new LinkedHashSet<Museum>();
        }
        museums.add(museum);
    }
    public void addGroup(Group group) {
        // adaugam un singur grup turistic.
        if (groups == null) {
            groups = new LinkedHashSet<Group>();
        }
        groups.add(group);
        System.out.println("am adaugat grupul: " + group.getTimetable() + group.getMuseumCode());
    }
    public void addMuseums(LinkedHashSet<Museum> colectieMuzee) {
        museums.addAll(colectieMuzee);
    }
    public void addGroups(LinkedHashSet<Group> colectieGrupuri) {
        groups.addAll(colectieGrupuri);
    }
    public void showMuseums(PrintWriter pw){
        Iterator<Museum> iterator = museums.iterator();
        while (iterator.hasNext()) {
            Museum museum = iterator.next();
            pw.println(museum.toString());
        }
    }
    // Metoda pentru a gasi ulterior un MAIN GROUP.
    // throws GroupNotExistsException  sau SA O PUN AICI>????? MA MAI GANDESC.,
    public Group findGroup(Integer museumCode, String timetable) {
        if (groups == null || groups.isEmpty()) {
            return null;
        }
        Iterator<Group> iterator = groups.iterator();
        while (iterator.hasNext()) {
            Group group = iterator.next();
            System.out.println("TESTT:" + group.getTimetable());
            if (museumCode.equals(group.getMuseumCode()) && timetable.equals(group.getTimetable())) {
                return group;
            }
        }
        return null;
    }
    public Set<Group> getListaGrupuri() {
        return groups;
    }
    public void resetAll() {
        if (museums != null) {
            museums.clear();
        }
        if (groups != null) {
            groups.clear();
        }
    }
    public void removeMemberOfGroup(Group group, Person member) throws PersonNotExistsException  {
        try {
            group.removeMember(member);
        } catch (PersonNotExistsException e) {
            throw new PersonNotExistsException("PersonNotExistsException: Person was not found in the group.");
        }
    }
    public void addMemberOfGroup(Group group, Person member) throws GroupTresholdException {
        try {
            Group grupGasit = this.findGroup(group.getMuseumCode(), group.getTimetable());
            if (grupGasit != null) {
                grupGasit.addMember(member);
            }
        } catch (GroupTresholdException e) {
            throw new GroupTresholdException("GroupThresholdException: Group cannot have more than 10 members.");
        }
    }
    public Museum findMuseum(long museumCode) {
        if (museums == null || museums.isEmpty()) {
            return null;
        }
        Iterator<Museum> iterator = museums.iterator();
        while (iterator.hasNext()) {
            Museum museum = iterator.next();
            if (museum.getCode() == museumCode) {
                return museum;
            }
        }
        return null;
    }
}

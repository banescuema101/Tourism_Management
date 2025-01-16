package org.example;
import java.io.PrintWriter;
import java.util.*;

public class Group{
    // aici pot face genericitatea, cu T fiind orice extinde Person, adica
    private List<Person> members;
    private Professor guide;
    private Integer museumCode;
    private String timetable;
    private Museum museum; // are un anumit muzeu.

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

    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<Person>();
//        if (guide instanceof Professor) {
//            this.guide = (Professor) guide;
//        } else {
//            this.guide = null;
//        }
        this.museumCode = museumCode;
        this.timetable = timetable;
    }
    public void addGuide(Person guide) throws GuideTypeException, GuideExistsException {
        if (!(guide instanceof Professor)) {
            throw new GuideTypeException("GuideTypeException: Guide must be a professor.");
        }
        if (this.guide != null) {
           throw new GuideExistsException("GuideExistsException: Guide already exists.");
        }
        this.guide = (Professor) guide;
    }
    public void addMember(Person member) throws GroupTresholdException {
        if (members.size() >= 10) {
            throw new GroupTresholdException("GroupThresholdException: Group cannot have more than 10 members.");
        } else {
            members.add(member);
        }
    }
    public void removeMember(Person member) throws PersonNotExistsException {
        boolean found;
        System.out.println("NUUUUU" + member);
        if (members.isEmpty()) {
            throw new PersonNotExistsException("PersonNotExistsException: Person was not found in the group.");
        } else {
            found = false;
            Iterator<Person> iterator = members.iterator();
            while (iterator.hasNext()) {
                Person person = iterator.next();
                System.out.println("person equals member?" + person.equals(member));
                System.out.println(person.toString());
                System.out.println(member.toString());
                if (person.equals(member)) {
                    // Dacă găsim membrul, îl eliminăm
                    iterator.remove();  // elimină membrul din set folosind iterator
                    found = true;
                    System.out.println("L-am sters");
                    break; // ieșim din metodă
                }
            }
            if (!found) {
                System.out.println("NU L AM GASIT PE MEMBRU CA SA IL STERG");
                throw new PersonNotExistsException("PersonNotExistsException: Person was not found in the group.");
            }
        }
    }
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
    public void resetGuide() {
        this.guide = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(museumCode, group.museumCode) && Objects.equals(timetable, group.timetable);
    }
}

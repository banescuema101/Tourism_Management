package org.example;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class that models a group, which has a list of members, as well as a guide attribute
 * of type Professor, a museum code that it will visit within a certain time interval,
 * i.e., the timetable of type String.
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
    * Constructor of the group, where I will initialize the list of group members as well as
    * the following fields:
    * @param museumCode the code of the museum
    * @param timetable the timetable that I will assign.
    */
    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<Person>();
        this.museumCode = museumCode;
        this.timetable = timetable;
    }

    /**
    * Method to add a guide to a group.
    * @param guide The person I want to assign as the group's guide, and I will check
    *              if they are of type {@link Professor}.
    * @throws GuideTypeException If they are not, the ADD GUIDE command,
    *              which will be executed later in the application, will not be allowed to assign them,
    *              so I will throw a GuideTypeException.
    * @throws GuideExistsException If the group already has a guide assigned, I will throw a
    *                              GuideExistsException to the respective command
    *                              {@link CommandAddGuideToGroup}.
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
    * Method to add a member to the group's list of people.
    * @param member The member I want to add.
    * @throws GroupTresholdException Throws this exception if the maximum allowed limit
    * of group members (namely: 10) is exceeded.
    */
    public void addMember(Person member) throws GroupTresholdException {
        if (members.size() >= 10) {
            throw new GroupTresholdException("GroupThresholdException: Group cannot have more than 10 members.");
        } else {
            members.add(member);
        }
    }

    /**
     * Method to remove a specific member from the group's list of members.
     * @param member The member I want to remove from the group's list if found,
     *               using an Iterator<Person>, which helps me remove the element
     *               found in the iterator from the list.
     * @throws PersonNotExistsException Throws this exception if the member I wanted
     * to remove was not found.
     * */
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
    * Method to check if the group's guide matches the one passed as a parameter
    * (later, in the findGuide command, I will need to search for a guide in a specific group
    * from the database, which is why I created this method here, so that in the class
    * {@link CommandFindGuide}, I only handle the case where the exception below is thrown).
    * @param person Parameter of type Person, representing the person I want to check
    *               if they are the guide of the current group.
    * @param pw PrintWriter where I will output the result of the action.
    * @throws GuideTypeException Exception thrown if the person I want to check
    *                            as the guide of the current group is not of type Professor.
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
     * Method to reset the guide of a group.
     */
    public void resetGuide() {
        this.guide = null;
    }
}

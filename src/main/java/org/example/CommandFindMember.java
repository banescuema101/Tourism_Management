package org.example;
import java.io.PrintWriter;

/**
 * Command in care voi vrea sa caut un membru dintr-un anume grup
 * turistic.
 */
public class CommandFindMember extends Command {
    private final Integer museumCode;
    private final String timetable;
    private final PrintWriter pw;
    private final Person person;
    public CommandFindMember(Integer museumCode, String timetable, Person person, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.pw = pw;
        this.person = person;
    }

    /**
    * The method will try to find the desired group in the database, then
    * @throws GroupNotExistsException The exception will be thrown and handled in Main, in case the specified group
    *     defined by the two parameters: museumCode and timetable DOES NOT exist in the database.
    *     If found, I will iterate through the members of the respective group, and if I find a member
    *     person that matches the person passed as a parameter to this command => I will display the corresponding message
    *     in the file. If the member I was looking for is not found => I will display member not exists.
    *
    */
    @Override
    public void execute() throws GroupNotExistsException {
        Group foundGroup = Database.Instance().findGroup(museumCode, timetable);
        if (foundGroup == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist");
        } else {
            boolean found = false;
            for (Person member : foundGroup.getMembers()) {
                if (member.equals(person)) {
                    pw.println(museumCode + " ## " + timetable + " ## member found: " + person);
                    found = true;
                    break;
                }
            }
            if (!found) {
                pw.println(museumCode + " ## " + timetable + " ## member not exists: " + person);
            }
        }
    }
}

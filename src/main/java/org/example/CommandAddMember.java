package org.example;
import java.io.PrintWriter;

/**
 * Command to add a member to a specific group.
 */
public class CommandAddMember extends Command {
    private final Integer museumCode;
    private final String timetable;
    private final Person member;
    private final PrintWriter pw;
    public CommandAddMember(Integer museumCode, String timetable, Person member, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.member = member;
        this.pw = pw;
    }
    @Override
    public void execute() throws GroupNotExistsException {
            // I locate the group in the database.
            Group foundGroup = Database.Instance().findGroup(museumCode, timetable);
            if (foundGroup == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
            try {
                // If I found it, then I call the method to add a member to the group
                // and write the output of the action to the output file, or a threshold error
                // in case the limit is not exceeded and the addMember method throws that exception.
                foundGroup.addMember(member);
                pw.println(museumCode + " ## " + timetable + " ## new member: " + member.toString());
            } catch (GroupTresholdException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + member.toString() + ")");
            }
    }
}

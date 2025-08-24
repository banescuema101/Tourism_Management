package org.example;

import java.io.PrintWriter;

/**
 * Class for removing a specific member from a group.
 */
public class CommandRemoveMember extends Command {
    private final Integer museumCode;
    private final String timetable;
    private final Person person;
    private final PrintWriter pw;
    public CommandRemoveMember(Integer museumCode, String timetable, Person person, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.person = person;
        this.pw = pw;
    }

    /**
    * The method removes a member (the parameter Person person passed to the constructor), first finding
    * the group in the database whose museumCode and timetable match those given as parameters
    * to this command, and if found, calls the removeMember method on the person. Since removeMember() can
    * throw an exception, it must be caught in this method (catch block).
    * @throws GroupNotExistsException the method throws an exception if the group is not found in the database.
    */
    @Override
    public void execute() throws GroupNotExistsException {
        try {
            Group foundGroup =  Database.Instance().findGroup(museumCode, timetable);
            if (foundGroup == null) {
                throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
            }
            foundGroup.removeMember(person);
            pw.println(museumCode + " ## " + timetable + " ## removed member: " + person.toString());
        } catch (PersonNotExistsException e) {
            pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (" + person.toString() + ")");
        }
    }
}

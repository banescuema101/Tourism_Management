package org.example;

import java.io.PrintWriter;

/**
  * Command to check if a guide passed as a parameter belongs to the current group.
  */
public class CommandFindGuide extends Command {
    private final Integer museumCode;
    private final String timetable;
    private final PrintWriter pw;
    private final Person person;
    public CommandFindGuide(Integer museumCode, String timetable, Person person, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.pw = pw;
        this.person = person;
    }

    /**
    * The execute() method of the Find Guide command calls the method in the database to find a
    * group, automatically returning the found group in case of success. Thus, here I will be able to call findGuide()
    * on the found group and check if the person passed as a parameter to the constructor of the
    * CommandFindGuide class is indeed the guide of the found group.
    * @throws GroupNotExistsException The exception will be thrown and handled in Main, in case the specified group
    * defined by the two parameters: museumCode and timetable DOES NOT exist in the database.
    */
    @Override
    public void execute() throws GroupNotExistsException {
        Group foundGroup = Database.Instance().findGroup(museumCode, timetable);
        if (foundGroup == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist");
        } else {
            try {
                foundGroup.findGuide(person, pw);
            } catch (GuideTypeException e) {
                pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new guide: " + person);
            }
        }
    }
}

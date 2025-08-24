package org.example;

import java.io.PrintWriter;

/**
 * Command to delete, remove the guide from a specific group.
 */
public class CommandRemoveGuide extends Command {
    private final Integer museumCode;
    private final String timetable;
    private final PrintWriter pw;
    // Here I store as an attribute the guide I will find, because I need it in Main
    // for clarifying an exception. See the REMOVE GUIDE operation there.
    private Person guideToReset;

    public Person getGuideToReset() {
        return guideToReset;
    }

    public void setGuideToReset(Person guideToReset) {
        this.guideToReset = guideToReset;
    }

    public CommandRemoveGuide(Integer museumCode, String timetable, PrintWriter pw) {
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.pw = pw;
    }

    /**
    * I will find the group in the database by calling findGroup. If I do not find it, I will throw an exception.
    * I will call the setter setGuideToReset on the found group, reset the group's guide (delete it, set it to null),
    * and then display the action's output in the corresponding PrintWriter.
    *
    * @throws GroupNotExistsException the exception thrown if the group does not exist in the database.
    */
    @Override
    public void execute() throws GroupNotExistsException {
        Group foundGroup = Database.Instance().findGroup(museumCode, timetable);
        if (foundGroup == null) {
            throw new GroupNotExistsException("GroupNotExistsException: Group does not exist.");
        } else {
            setGuideToReset(foundGroup.getGuide());
            foundGroup.resetGuide();
            // Display the removed guide: the guide that the found group previously had.
            // This is why I first store it as an attribute in the CommandRemoveGuide class,
            // because the found group will have its guide set to null after the reset.
            pw.println(museumCode + " ## " + timetable + " ## removed guide: " + guideToReset.toString());
        }
    }
}

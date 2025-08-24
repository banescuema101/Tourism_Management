package org.example;

import java.io.PrintWriter;
import java.util.Set;

/**
 * Command for adding a guide to a group.
 */
public class CommandAddGuideToGroup extends Command {
    private final Group group;
    private final Person guide;
    private final PrintWriter pw;

    /**
    * Constructor for the specific command to add a guide to a group.
    * @param group The group to which I want to assign the guide.
    * @param guide The guide I want to add.
    * @param pw The PrintWriter where I will write the result of the command (in case of success/failure).
    */
    public CommandAddGuideToGroup(Group group, Person guide, PrintWriter pw) {
        this.group = group;
        this.guide = guide;
        this.pw = pw;
    }
    @Override
    public void execute() {
        Set<Group> groupsList = Database.Instance().getGroupsList();
        try {
            // I need to update the "group's guide" in the database!! That's why I'm doing it here.
            if (groupsList.contains(group)) {
                group.addGuide(guide);
                // Initially, I thought about removing the group, then modifying it, and then reintroducing it again,
                // but I noticed that this changed the order in the set and caused problems.
                pw.println(group.getMuseumCode() + " ## " + group.getTimetable() + " ## new guide: " + group.getGuide().toString());
                // Now, I need to attach the observer (the guide) to the museum with MuseumCode code.
                Museum museumWithObserver = Database.Instance().findMuseum(group.getMuseumCode());
                if (museumWithObserver != null) {
                    museumWithObserver.attachObserver((Professor) guide);
                }
            }
        } catch (GuideTypeException e) {
            pw.println(group.getMuseumCode() + " ## " + group.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + guide.toString() + ")");
        } catch (GuideExistsException e) {
            // here I am displaying an error message New Guide: The guide that already exists
            pw.println(group.getMuseumCode() + " ## " + group.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + group.getGuide().toString() + ")");
        }
    }
}

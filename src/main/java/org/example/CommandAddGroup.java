package org.example;

/**
 * Command with the purpose of adding a group in the database.
 */
public class CommandAddGroup extends Command {
    private final Group group;
    public CommandAddGroup(Group group) {
        this.group = group;
    }
    // I will call the database method for adding a group if and only if
    // the museum is not overloaded at that specific timetable moment.
    // (If there are already at least 3 groups scheduled, no new group will be added,
    // and an exception will be thrown, which will be handled later in Main.)
    public void execute() throws MuseumFullException {
        if (Database.Instance().museumAvailability(group.getMuseumCode(), group.getTimetable())) {
            Database.Instance().addGroup(group);
        } else {
            throw new MuseumFullException("Unfortunately, the maximum number of groups for this time slot has been reached.");
        }
    }
}

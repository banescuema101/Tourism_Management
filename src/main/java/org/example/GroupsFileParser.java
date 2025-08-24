package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
* Class where I effectively read data from a groups-type file and display the corresponding output.
* I execute commands based on what is in the first column of each line (except the first one),
* create specific commands with the corresponding extracted parameters, and then execute them.
*/
public class GroupsFileParser {
    public static void parsareLiniiGroups(BufferedReader br, PrintWriter pw) throws IOException {
        String line;
        br.readLine();
        while((line = br.readLine()) != null) {
            // I split the line based on the | symbol, which is the delimiter in the input file.
            String[] words = line.split("\\|");
            String command = words[0];
            String firstName = words[1];
            String lastName = words[2];
            String role = words[3];
            // if I do not find the term
            // in the CSV file, I set it to a default impossible value: -1.
            int age = -1;
            if (!words[4].isEmpty()) {
                age = Integer.parseInt(words[4]);
            }
            String email = words[5];
            if (email.isEmpty()) {
                email = "null";
            }
            String school = words[6];
            int yearOfStudyOrExperience = -1;
            if(!words[7].isEmpty()) {
                yearOfStudyOrExperience = Integer.parseInt(words[7]);
            }
            String groupRole = words[8];
            Person parsedPerson;
            // the person is either a student or a professor:
            if (role.equals("student")) {
                parsedPerson = new Student(firstName, lastName, role, school, yearOfStudyOrExperience);
            } else {
                parsedPerson = new Professor(firstName, lastName, role, school, yearOfStudyOrExperience);
            }
            // Setting the specific fields.
            parsedPerson.setAge(age);
            parsedPerson.setEmail(email);
            parsedPerson.setRole(groupRole);
            int museumCode = -1;
            // First, I check if it is not null. Otherwise, the parseInt method would throw an
            // error on a null parameter.
            if (!words[9].isEmpty()) {
                museumCode = Integer.parseInt(words[9]);
            }
            String timetable =  words[10];
            // I will process each type of command so that I use the corresponding Command design,
            // and I will also display an error message in the file for each command (except the first one, ADD GUIDE)
            // in case the respective Command throws an exception of type GroupNotExistException.
            switch(command) {
                case "ADD GUIDE":
                // I try to add a guide to the group with the specified timetable and museum code,
                // but if I do not find the group in the database, I will create it and then set
                // the corresponding guide.
                    Group group = new Group(museumCode, timetable);
                    Group foundGroup = Database.Instance().findGroup(museumCode, timetable);
                    if (foundGroup != null) {
                        CommandAddGuideToGroup commandAddGuideToGroup = new CommandAddGuideToGroup(foundGroup, parsedPerson, pw);
                        commandAddGuideToGroup.execute();
                    } else {
                        // If the MAIN GROUP doesn't exist:
                        if (parsedPerson instanceof Professor) {
                            CommandAddGroup commandAddGroup = new CommandAddGroup(group);
                            try {
                                commandAddGroup.execute();
                            // if a MuseumFullException was thrown => I can no longer execute
                            // the command to add this group as a visitor to the museum.
                            } catch (MuseumFullException e) {
                                pw.println(e.getMessage());
                            }
                            // System.out.println(group.getGuide());
                            CommandAddGuideToGroup commandAddGuide = new CommandAddGuideToGroup(group, parsedPerson, pw);
                            commandAddGuide.execute();
                        }
                    }
                    break;
                case "ADD MEMBER":
                    try {
                        CommandAddMember commandAddMember = new CommandAddMember(museumCode, timetable, parsedPerson, pw);
                        commandAddMember.execute();
                    } catch (GroupNotExistsException e) {
                        pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + parsedPerson + ")");
                    }
                    break;
                case "REMOVE GUIDE":
                    CommandRemoveGuide commandRemoveGuide = new CommandRemoveGuide(museumCode, timetable, pw);
                    try {
                        commandRemoveGuide.execute();
                    } catch (GroupNotExistsException e) {
                        pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + "## (removed member: " + commandRemoveGuide.getGuideToReset().toString() + ")");
                    }
                    break;
                case "REMOVE MEMBER":
                    CommandRemoveMember commandRemoveMember = new CommandRemoveMember(museumCode, timetable, parsedPerson, pw);
                    try {
                        commandRemoveMember.execute();
                    } catch (GroupNotExistsException e) {
                        pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (removed member: " + parsedPerson + ")");
                    }
                    break;
                case "FIND GUIDE":
                    CommandFindGuide commandFindGuide = new CommandFindGuide(museumCode, timetable, parsedPerson, pw);
                    try {
                        commandFindGuide.execute();
                    } catch (GroupNotExistsException e) {
                        pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (find guide: " + parsedPerson + ")");
                    }
                    break;
                case "FIND MEMBER":
                    CommandFindMember commandFindMember = new CommandFindMember(museumCode, timetable, parsedPerson, pw);
                    try {
                        commandFindMember.execute();
                    } catch (GroupNotExistsException e) {
                        pw.println(museumCode + " ## " + timetable + " ## " + e.getMessage() + " ## (find member: " + parsedPerson + ")");
                    }
                    break;
                    // Analyse museums => I display all museums sorted by the number of groups they have.
                case "ANALYSE MUSEUMS":
                    List<Museum> sortedMuseums = Database.Instance().displayMuseumsSortedByGroups();
                    for (Museum m : sortedMuseums) {
                        System.out.println(m);
                    }
                default:
                    break;
            }
        }
        br.close();
        pw.close();
    }
}

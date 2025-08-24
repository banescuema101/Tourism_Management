package org.example;
import java.util.*;

/**
 * Here we have the Database, created using Singleton design pattern, which contains a museums set and a groups set.
 */
public class Database {
    private static Database uniqueDatabase;
    private Set<Museum> museums;
    private Set<Group> groups;
    private Database() {}

    /**
     * I used the Singleton design pattern to ensure that this class can be instantiated
     * only once. If multiple instantiations are attempted, the reference will always point
     * to the same unique object that was created the first time.
     * @return an instance of the class, the Database object.
     */
    public static Database Instance() {
        if (uniqueDatabase == null) {
            uniqueDatabase = new Database();
        }
        return uniqueDatabase;
    }

    /**
     * Method used to add a museum to the LinkedHashSet (to maintain the order
     * in which they are added, ensuring consistency one after another).
     * @param museum The museum I want to add to the database set.
     */
    public void addMuseum(Museum museum) {
        // adauga o singura entitate muzeala.
        if (museums == null) {
            museums = new LinkedHashSet<>();
        }
        museums.add(museum);
    }

    /**
     * Method used to add a group of people to the set of groups in the database.
     * @param group The group to be added to the set.
     */
    public void addGroup(Group group) {
        if (groups == null) {
            groups = new LinkedHashSet<>();
        }
        groups.add(group);
    }

    // adding the collections of museums and groups, but I did not need these methods.
    public void addMuseums(LinkedHashSet<Museum> museumCollection) {
        museums.addAll(museumCollection);
    }
    public void addGroups(LinkedHashSet<Group> groupsCollection) {
        groups.addAll(groupsCollection);
    }

    /**
     * Method to later find a MAIN GROUP.
     * @param museumCode the code of the museum to match.
     * @param timetable the timetable provided as a parameter.
     * @return Returns the group whose museumCode and timetable match the given ones.
     */
    public Group findGroup(Integer museumCode, String timetable) {
        if (groups == null || groups.isEmpty()) {
            return null;
        }
        // I iterate through all the groups stored in the database set and return the group
        // if the two conditions below are satisfied:

        for (Group group : groups) {
            if (museumCode.equals(group.getMuseumCode()) && timetable.equals(group.getTimetable())) {
                return group;
            }
        }
        return null;
    }
    public Set<Group> getGroupsList() {
        return groups;
    }

    /**
     * This method helps me "clear the database" by removing all elements from the
     * two sets of groups and museums. I need to call this method in the {@link Main} class,
     * at the beginning, right after the Database.Instance() is created or reused (since it is a singleton),
     * because I do not want the data to persist between different query sessions
     * (separate threads from the TestMain.java class).
     */
    public void resetAll() {
        if (museums != null) {
            museums.clear();
        }
        if (groups != null) {
            groups.clear();
        }
    }

    /**
     * Method intended to find a museum whose code matches a museum in the database set.
     * If such a museum with the given code is found, it will be returned.
     * @param museumCode the code of the museum I want to search for in the database
     * @return the museum, if found;
     *         null otherwise.
     */
    public Museum findMuseum(long museumCode) {
        if (museums == null || museums.isEmpty()) {
            return null;
        }
        for (Museum museum : museums) {
            if (museum.getCode() == museumCode) {
                return museum;
            }
        }
        return null;
    }

    /**
     * Method -> Extra functionality.
     * If we consider that only a maximum of three visitor groups (maximum 33 people, since
     * a group can contain only 10 members plus the guide teacher) can visit the museum within a
     * specific time slot. (Otherwise, it would be too crowded and could affect the sensitive
     * elements inside the museum.)
     * Moreover, we aim to provide an exclusive, high-quality experience, so we do not allow
     * the overlapping of multiple visitor groups within the same time slot (lasting a few dozen minutes).
     * @param museumCode the code of the museum where I want to add a group.
     * @param timetable the time slot in which a group wishes to visit the museum with the given museumCode.
     * @return true if another group can be added as a visitor to the museum, false otherwise.
     */
    public boolean museumAvailability(Integer museumCode, String timetable) {
        int groupsNumber = 0;
        if (groups == null || groups.isEmpty()) {
            return true;
        }
        for (Group group : groups) {
            if (group.getMuseumCode().equals(museumCode) && group.getTimetable().equals(timetable)) {
                groupsNumber++;
            }
        }
        if (groupsNumber >= 3) {
            return false;
        }
        return true;
    }

    /**
     * Method also for the extra functionality part, where I want to display
     * each existing museum, ordered by the number of groups that have visited it
     * up to the moment this method is called.
     * @return the list of museums sorted accordingly using the getSortedMuseums method
     */
    public List<Museum> displayMuseumsSortedByGroups() {
        if (museums == null || museums.isEmpty()) {
            return new ArrayList<>();
            // if there are no museums, I would return an empty list.
        }
        // a number for each museum, how many groups have been assigned to it up to this moment.
        Map<Museum, Integer> museumsAndGroups = new HashMap<>();
        int groupsNumber = 0;
        for (Museum museum : museums) {
            if (groups == null || groups.isEmpty()) {
                groupsNumber = -1;
            }
            for (Group group : groups) {
                if (((long)(group.getMuseumCode())) == museum.getCode()) {
                    groupsNumber++;
                }
            }
            // If there are no museums that haven't had any tourist groups visit them,
            // I will not consider them for sorting.
            if (groupsNumber != -1) {
                museumsAndGroups.put(museum, groupsNumber);
            }
        }
        return getSortedMuseums(museumsAndGroups);
    }

    /**
     * Helper method where I use the {@link List#sort(Comparator)} method with an anonymous
     * comparator that compares the values in the list of keys corresponding to the first
     * and second museums being compared.
     * @param museumsAndGroups the map passed as a parameter
     * @return the sorted list.
     */
    private List<Museum> getSortedMuseums(Map<Museum, Integer> museumsAndGroups) {
        // I am converting the keySet of the map to a list.
        List<Museum> museumsList = new ArrayList<>(museumsAndGroups.keySet());
        // I will use an anonymous class here, because initially I tried to create a separate
        // comparator that also overrides the compare method, but I couldn’t pass the number
        // of groups per museum... And I think it didn’t make sense to introduce an additional
        // attribute in the Museum class to store the number of visits just to implement a
        // sorting functionality here, since I wouldn’t use it anywhere else.
        museumsList.sort(new Comparator<Museum>() {
            @Override
            public int compare(Museum museum1, Museum museum2) {
                if (museumsAndGroups.get(museum1) > museumsAndGroups.get(museum2)) {
                    return -1;
                } else if (museumsAndGroups.get(museum1) < museumsAndGroups.get(museum2)) {
                    return 1;
                }
                return 0;
            }
        });
        return museumsList;
    }
}

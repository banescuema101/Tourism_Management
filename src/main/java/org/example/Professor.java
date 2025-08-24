package org.example;

/**
 * The Professor class models a professor, extends the Person class, overrides the
 * toString method for the proper representation of the object, and creates a specific
 * constructor using the one from the superclass Person.
 */
public class Professor extends Person{
    private final int experience;
    private final String school;
    public Professor(String surname, String name, String role, String school, int experience) {
        super(surname, name, role);
        this.experience = experience;
        this.school = school;
    }
    public String toString() {
        return super.toString() + ", school=" + school + ", experience=" + experience;
    }
}

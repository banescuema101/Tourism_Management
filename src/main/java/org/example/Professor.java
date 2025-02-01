package org.example;

/**
 * Clasa Professor, va modela un professor, ce va extinde clasa Person, suprascriu metoda
 * toString pentru reprezentarea corespunzatoare a obiectului si creez constructor specific
 * folosindu-ma de cel al superclasei Person.
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

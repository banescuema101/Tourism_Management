package org.example;

public class Professor extends Person{
    private int experience;
    private String school;
    public Professor(String surname, String name, String role, String school, int experience) {
        super(surname, name, role);
        this.experience = experience;
        this.school = school;
    }
    public String toString() {
        return super.toString() + ", school=" + school + ", experience=" + experience;
    }
}

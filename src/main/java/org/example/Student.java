package org.example;

public class Student extends Person {
    private String school;
    private int studyYear;
    public Student(String surname, String name, String role, String school, int studyYear) {
        super(surname, name, role);
        this.school = school;
        this.studyYear = studyYear;
    }
    public String toString() {
        return super.toString() + ", school=" + school + ", studyYear=" + studyYear;
    }
    public boolean mayBeGuide() {
        return false;
    }
}

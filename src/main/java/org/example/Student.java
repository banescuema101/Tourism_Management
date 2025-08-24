package org.example;

/**
 * Class that models a Student, which will have a specific constructor where I also call
 * the superclass Person's constructor and also implement the toString method for the specific
 * representation of a student.
 */
public class Student extends Person {
    private final String school;
    private final int studyYear;
    public Student(String surname, String name, String role, String school, int studyYear) {
        super(surname, name, role);
        this.school = school;
        this.studyYear = studyYear;
    }
    public String toString() {
        return super.toString() + ", school=" + school + ", studyYear=" + studyYear;
    }
}

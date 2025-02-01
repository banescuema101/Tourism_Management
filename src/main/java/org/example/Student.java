package org.example;

/**
 * Clasa ce modeleaza un Student, care va avea un constructor specific in care il apelez si pe
 * cel al superclasei Person si implementez de asemenea si metoda toString pentru reprezentarea
 * specifica unui student.
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

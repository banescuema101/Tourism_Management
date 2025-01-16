package org.example;

import java.io.PrintWriter;
public class Person implements Observer{
    private String surname;
    private String name;
    private String role;
    private int age;
    private String email = null;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(String surname, String name, String role) {
        this.surname = surname;
        this.name = name;
        this.role = role;
    }
    public String toString() {
        return "surname=" + this.surname + ", name=" + this.name + ", role=" + this.role + ", age=" + this.age + ", email=" + this.email;
    }

    /**
     * Aici am suprascris metoda equals, pentru a putea compara corect si fara probleme, atunci cand
     * am avut nevoie sa caut un anumit membru, spre exemplu in ComandaFindMember. Cand in lista de membrii,
     * membrul curent era egal cu mebrul de cautat {@param person}, am aplicat equals si am facut in aceasta metoda
     * astfel incat equals sa dea true daca si numai daca toti parametrii clasei {@link Person} sunt aceeasi pentru ambele
     * obiecte de comparat.
     * @param person Persoana pe care o compar cu referinta la obiectul curent.
     * @return true, daca obiectele sunt identice, false altfel.
     */
    public boolean equals(Person person) {
        if (surname == null || !this.surname.equals(person.getSurname())) {
            return false;
        }
        if (name == null || !this.name.equals(person.getName())) {
            return false;
        }
        if (role == null || !this.role.equals(person.getRole())) {
            return false;
        }
        if (age != person.getAge()) {
            return false;
        }
        if (email == null || !email.equals(person.getEmail())) {
            return false;
        }
        return true;
    }

    /**
     * Metoda care updateaza continutul fisierului de iesire pentru evenimente.
     * @param message mesajul pe care ajung sa il scriu in fisier.
     * @param pw PrintWriterul cu care voi scrie.
     */
    @Override
    public void update(String message, PrintWriter pw) {
            // partea DE OBSERVER DESIGN PATTERN:
            pw.println(message);
    }
}
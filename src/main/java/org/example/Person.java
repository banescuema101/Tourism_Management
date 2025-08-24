package org.example;

import java.io.PrintWriter;

/**
* Class that models a person. It is not abstract because I need to be able to instantiate it,
* for example, in the case where I create a museum manager (who is neither
* a professor nor a student, but just a Person).
*/
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
    * Here I have overridden the equals method to correctly and seamlessly compare when
    * I needed to search for a specific member, for example in CommandFindMember. When in the list of members,
    * the current member was equal to the searched member {@param person}, I applied equals and made it in this method
    * so that equals returns true if and only if all the parameters of the {@link Person} class are the same for both
    * objects being compared.
    * @param person The person I compare with the reference to the current object.
    * @return true, if the objects are identical, false otherwise.
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
    * Method that updates the content of the output file for events.
    * @param message the message that will be written to the file.
    * @param pw the PrintWriter used to write the message to the file.
    */
    @Override
    public void update(String message, PrintWriter pw) {
            // partea DE OBSERVER DESIGN PATTERN:
            pw.println(message);
    }
}
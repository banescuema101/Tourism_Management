package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Clasa in care efectiv citesc datele dintr-un fisier de tip groups, si afisez outputul
 * corespunzator. Execut comenzile in functie de ce este pe prima coloana a fiecarei linii
 * (cu exceptia primei) si creez comenzile specifice cu parametrii corespunzatori
 * preluati, apoi le execut.
 */
public class ParsareFisierGroups {
    public static void parsareLiniiGroups(BufferedReader br, PrintWriter pw) throws IOException {
        String linie;
        br.readLine();
        while((linie = br.readLine()) != null) {
            // preiau pe rand toti parametri separati prin simbolul |.
            String[] sir = linie.split("\\|");
            String comanda = sir[0];
            String prenume = sir[1];
            String nume = sir[2];
            String titulatura = sir[3];
            // daca nu gasesc termenul
            // in csv file, atunci il pun default pe o valoare imposibila: -1.
            int varsta = -1;
            if (!sir[4].isEmpty()) {
                varsta = Integer.parseInt(sir[4]);
            }
            String email = sir[5];
            if (email.isEmpty()) {
                email = "null";
            }
            String scoala = sir[6];
            int anStudiiSauExperienta = -1;
            if(!sir[7].isEmpty()) {
                anStudiiSauExperienta = Integer.parseInt(sir[7]);
            }
            String rol = sir[8];
            Person persoanaParsata;
            // persoana va putea fi ori student ori profesor:
            if (titulatura.equals("student")) {
                persoanaParsata = new Student(prenume, nume, titulatura, scoala, anStudiiSauExperienta);
            } else {
                persoanaParsata = new Professor(prenume, nume, titulatura, scoala, anStudiiSauExperienta);
            }
            // ii setez campurile specifice.
            persoanaParsata.setAge(varsta);
            persoanaParsata.setEmail(email);
            persoanaParsata.setRole(rol);
            int codeMuseum = -1;
            // verific in primul rand daca nu-i null, altfel ar da eroare metoda parseInt pe un parametru null.
            if (!sir[9].isEmpty()) {
                codeMuseum = Integer.parseInt(sir[9]);
            }
            String timetable =  sir[10];
            // Voi prelucra fiecare tip de comanda, astfel incat sa folosesc design-ul Command corespunzator,
            // si voi si afisa la fiecare comanda(cu exceptia primei, cea de ADD GUIDE) un mesaj de
            // eroare in fisier in caz ca respectiva Comanda a aruncat o exceptie de tipul
            // GroupNotExistException.
            switch(comanda) {
                case "ADD GUIDE":
                    // incerc sa adaug un ghid grupului cu timetableul si codeMuseul specificat,
                    // insa in caz ca nu gasesc grupul in baza de date, o sa il creez si apoi voi seta
                    // ghidul corespunzator.
                    Group grup = new Group(codeMuseum, timetable);
                    Group grupGasit = Database.Instanta().findGroup(codeMuseum, timetable);
                    if (grupGasit != null) {
                        ComandaAddGuideToGroup comandaAddGuide = new ComandaAddGuideToGroup(grupGasit, persoanaParsata, pw);
                        comandaAddGuide.executa();
                    } else {
                        // daca NU EXISTA UN MAIN GROUP=>
                        if (persoanaParsata instanceof Professor) {
                            ComandaAddGroup comandaAddGroup = new ComandaAddGroup(grup);
                            // aici e ceva ca il creez cu ghidul si apoi vreau sa il adaug si da exceptie ca deja il am EVIDENT.
                            try {
                                comandaAddGroup.executa();
                                // daca mi s aruncat exceptie de tipul MuseumFullException => nu mai pot realiza
                                // comanda de adaugare a acestui grup ca vizitator al muzeului.
                            } catch (MuseumFullException e) {
                                pw.println(e.getMessage());
                            }
//                            System.out.println(grup.getGuide());
                            ComandaAddGuideToGroup comandaAddGuide = new ComandaAddGuideToGroup(grup, persoanaParsata, pw);
                            comandaAddGuide.executa();
                        }
                    }
                    break;
                case "ADD MEMBER":
                    try {
                        ComandaAddMember comandaAddMember = new ComandaAddMember(codeMuseum, timetable, persoanaParsata, pw);
                        comandaAddMember.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + persoanaParsata + ")");
                    }
                    break;
                case "REMOVE GUIDE":
                    ComandaRemoveGuide comandaRemoveGuide = new ComandaRemoveGuide(codeMuseum, timetable, pw);
                    try {
                        comandaRemoveGuide.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + "## (removed member: " + comandaRemoveGuide.getGhidDeResetat().toString() + ")");
                    }
                    break;
                case "REMOVE MEMBER":
                    ComandaRemoveMember comandaRemoveMember = new ComandaRemoveMember(codeMuseum, timetable, persoanaParsata, pw);
                    try {
                        comandaRemoveMember.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (removed member: " + persoanaParsata + ")");
                    }
                    break;
                case "FIND GUIDE":
                    ComandaFindGuide comandaFindGuide = new ComandaFindGuide(codeMuseum, timetable, persoanaParsata, pw);
                    try {
                        comandaFindGuide.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (find guide: " + persoanaParsata + ")");
                    }
                    break;
                case "FIND MEMBER":
                    ComandaFindMember comandaFindMember = new ComandaFindMember(codeMuseum, timetable, persoanaParsata, pw);
                    try {
                        comandaFindMember.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (find member: " + persoanaParsata + ")");
                    }
                    break;
                    // pentru functionalitatea bonus de sortare pe care m-am gandit sa o implementez.
                case "ANALYSE MUSEUMS":
                    List<Museum> muzeeSortate = Database.Instanta().afisareMuzeeSortateDupaGrupuri();
                    for (Museum m : muzeeSortate) {
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

package org.example;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Baza de date creata folosind Singleton, ce cotine un set de muzee si de grupuri.
 */
public class Database {
    private static Database databaseUnica;
    private Set<Museum> museums;
    private Set<Group> groups;
    private Database() {}

    /**
     * Am utilizat designul Singleton pentru a asigura ca aceasta clasa se va putea instantia
     * o singura data. Daca se va incerca instantierea multipla, referinta la obiect va fi aceeasi
     * ca cea unica, care s-a creat prima data.
     * @return o instanta a clasei, obiectul de tip Database.
     */
    public static Database Instanta() {
        if (databaseUnica == null) {
            databaseUnica = new Database();
        }
        return databaseUnica;
    }

    /**
     * Metoda prin care adaug un muzeu in setul de tip LinkedHashSet (pt a mentine ordinea
     * in care le adaug, consecventa, unul dupa altul.)
     * @param museum Muzeul pe care vreau sa il adaug la setul bazei de date.
     */
    public void addMuseum(Museum museum) {
        // adauga o singura entitate muzeala.
        if (museums == null) {
            museums = new LinkedHashSet<Museum>();
        }
        museums.add(museum);
    }

    /**
     * Metoda prin care adaug un grup de persoane la setul de grupuri din baza de date.
     * @param group Grupul pe care il adaug in set.
     */
    public void addGroup(Group group) {
        if (groups == null) {
            groups = new LinkedHashSet<Group>();
        }
        groups.add(group);
    }
    // adaugarea colectiilor de muzee si grupuri, insa nu am avut nevoie de aceste metode.
    public void addMuseums(LinkedHashSet<Museum> colectieMuzee) {
        museums.addAll(colectieMuzee);
    }
    public void addGroups(LinkedHashSet<Group> colectieGrupuri) {
        groups.addAll(colectieGrupuri);
    }

    /**
     * Metoda de afisare intr-un fisier (PrintWriter) a tuturor elementelor
     * din cadrul setului de muzee din baza de date.
     * @param pw PrintWriterul in care voi scrie.
     */
    public void showMuseums(PrintWriter pw){
        for (Museum museum : museums) {
            pw.println(museum.toString());
        }
    }

    /**
     * Metoda pentru a gasi ulterior un MAIN GROUP.
     * @param museumCode codul muzeului caruia sa ii fac match.
     * @param timetable timetable-ul dat ca parametru.
     * @return Returnez grupul al carui museumCode si Timetable coincid cu cei pe care vreau sa fac match.
     */
    public Group findGroup(Integer museumCode, String timetable) {
        if (groups == null || groups.isEmpty()) {
            return null;
        }
        //iterez prin toate grupurile tinute in setul din baza de date si returnez grupul
        // daca se respecta cele doua conditii de mai jos:
        for (Group group : groups) {
            if (museumCode.equals(group.getMuseumCode()) && timetable.equals(group.getTimetable())) {
                return group;
            }
        }
        return null;
    }
    public Set<Group> getListaGrupuri() {
        return groups;
    }

    /**
     * Aceasta metoda ma ajuta sa ,,golesc baza de date", stergand toate elementele din cele
     * doua seturi de grupuri respectiv muzee. Va trebui sa apelez aceasta metoda in clasa {@link Main},
     * la inceput, imediat ce se creeaza/sau se reutilizeaza(fiind singleton) instanta Database.Instanta()
     * pentru ca nu vreau ca datele sa fie persistente intre sesiuni diferite de interogari (threaduri distincte
     * din clasa TestMain.java)
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
     * Metoda cu scopul de a gasi un muzeu al carui cod coincide cu muzeul din setul din baza de date
     * Daca gasesc un astfel de muzeu cu codul transmis ca parametru atunci il voi returna.
     * @param museumCode codul muzeului pe care diresc sa il caut in baza de date
     * @return muzeul, daca l am gasit
     * null, in caz contrar.
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
}

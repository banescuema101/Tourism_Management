package org.example;
import java.util.*;

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
            museums = new LinkedHashSet<>();
        }
        museums.add(museum);
    }

    /**
     * Metoda prin care adaug un grup de persoane la setul de grupuri din baza de date.
     * @param group Grupul pe care il adaug in set.
     */
    public void addGroup(Group group) {
        if (groups == null) {
            groups = new LinkedHashSet<>();
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

    /**
     *  Metoda ->  Functionalitate Extra.
     *  Daca as considera ca doar maxim trei grupuri de vizitatori (maxim 33 persoane adica, intrucat
     *  intr-un grup pot fi doar 10 membrii, plus profesorul ghid) ar putea vizita muzeul intr-un anumit interval
     *  orar.(in caz contrar ar fi foarte multa aglomeratie si ar putea afecta elementele sensibile din muzeu
     *  De altfel, dorim sa oferim o experienta exclusiva, de cea mai buna calitate, astfel nu se doreste suprapunerea
     *  mai multor grupuri de vizitatori intr-un anumit interval orar (de cateva zeci de minute).
     * @param museumCode codul muzeului la care vreau a adaug un grup.
     * @param timetable intervalul orar in care doreste un grup sa viziteze muzeul cu codul museumCode.
     * @return true daca se va putea adauga un alt grup ca vizitator al muzeului, false altfel.
     */
    public boolean disponibilitateMuzeu(Integer museumCode, String timetable) {
        int numarGrupuri = 0;
        if (groups == null || groups.isEmpty()) {
            return true;
        }
        for (Group group : groups) {
            if (group.getMuseumCode().equals(museumCode) && group.getTimetable().equals(timetable)) {
                numarGrupuri++;
            }
        }
        if (numarGrupuri >= 3) {
            return false;
        }
        return true;
    }

    /**
     * Metoda tot pentru partea de functionalitate extra, in care imi doresc sa afisez
     * fiecare muzeu existent, dupa numarul de grupuri care au venit sa il viziteze pana la momentul
     * de timp cand este apelata metoda aceasta.
     * @return lista de muzee sortata corespunzator cu ajutorul metodei getMuseumsSortate
     */
    public List<Museum> afisareMuzeeSortateDupaGrupuri() {
        if (museums == null || museums.isEmpty()) {
            return new ArrayList<>();
            // daca nu am niciun muzeu, as returna o lista goala.
        }
        // numar pentru fiecare muzeu, cate grupuri au fost asignate lui pana in momentul acesta
        Map<Museum, Integer> muzeuriSiGrupuri = new HashMap<>();
        int numarGrupuri = 0;
        for (Museum museum : museums) {
            if (groups == null || groups.isEmpty()) {
                numarGrupuri = -1;
            }
            for (Group group : groups) {
                if (((long)(group.getMuseumCode())) == museum.getCode()) {
                    numarGrupuri++;
                }
            }
            // daca un muzeu nu a avut niciun grup turistic care l-a vizitat
            // nu il voi lua in calcul pentru sortare.
            if (numarGrupuri != -1) {
                muzeuriSiGrupuri.put(museum, numarGrupuri);
            }
        }
        return getMuseumsSortate(muzeuriSiGrupuri);
    }

    /**
     * Metoda ajutatoare in care folosesc metoda din {@link List#sort(Comparator)}, cu un comparator
     * anonim care compara valorile din lista de chei corespunzatoare pentru primul si al doilea
     * muzeu de comparat.
     * @param muzeuriSiGrupuri map-ul transmis ca parametru
     * @return lista sortata.
     */
    private List<Museum> getMuseumsSortate(Map<Museum, Integer> muzeuriSiGrupuri) {
        // preiau dictonarul convertit la lista.
        List<Museum> listaMuzee = new ArrayList<>(muzeuriSiGrupuri.keySet());
        // voi folosi aici clasa anonima, pentru ca am incercat initial sa fac un comparator
        // separat care sa suprascrie si el metoda compare, dar nu am putut sa transmit
        // numarul de grupuri per muzeu... Si consider ca nu avea rost sa introduc un atribut
        // suplimentar la clasa Muzeu, care sa retina si numarul de vizite al muzeului doar
        // pentru a implementa aici o functionalitate de sortare, intrucat in alta parte
        // nu m as folosi de asta.
        listaMuzee.sort(new Comparator<Museum>() {
            @Override
            public int compare(Museum muzeu1, Museum muzeu2) {
                if (muzeuriSiGrupuri.get(muzeu1) > muzeuriSiGrupuri.get(muzeu2)) {
                    return -1;
                } else if (muzeuriSiGrupuri.get(muzeu1) < muzeuriSiGrupuri.get(muzeu2)) {
                    return 1;
                }
                return 0;
            }
        });
        return listaMuzee;
    }
}

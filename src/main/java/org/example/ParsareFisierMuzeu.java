package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clasa in care efectiv citesc datele dintr-un fisier de tip muzeum, si afisez outputul
 * corespunzator. Execut comenzile in functie de ce este pe prima coloana a fiecarei linii
 * (cu exceptia primei) si creez comenzile specifice cu parametrii corespunzatori
 * preluati, apoi le execut.
 */
public class ParsareFisierMuzeu {
    public static void parsareLiniiMuseum(BufferedReader br, PrintWriter pw) throws IOException {
        // nu e recomandat sa setez toate argumentele optionale, daca nu le primesc pe toate.
        String linie;
        br.readLine();
        while((linie = br.readLine()) != null) {
            try {
                String[] sir = linie.split("\\|");
                // incep cu parsarea parametrilor:
                String comanda = sir[0];
                long cod = Long.parseLong(sir[1]);
                String denumire = sir[2];
                String county = sir[3];
                long supervisorCode = Long.parseLong(sir[14]);
                Integer sirutaCode = Integer.parseInt(sir[16]);
                // verific sa nu parsez o valoare "" nula practic, si apoi voi avea
                // in mod eronat ca ar trebui pus atributul optional, dar nu trebuie defapt.
                Location.LocationBuilder locationBuilder = new Location.LocationBuilder(county, sirutaCode);
                // a trebuit sa inlocuiesc virgula cu caracterul vid, intrucat de exemplu
                // valoarea latitude = 412,546 trebuia luata ca fiind 412546, pentru a fi o valoare de
                // latitudine/longitudine valida.
                if (!sir[18].isEmpty()) {
                    int latitude = Integer.parseInt(sir[18].replace(",", ""));
                    locationBuilder.setLatitude(latitude);
                }
                if (!sir[19].isEmpty()) {
                    int longitude = Integer.parseInt(sir[19].replace(",", ""));
                    locationBuilder.setLongitude(longitude);
                }
                if (sir[0].equals("ADD MUSEUM")) {
                    if (!sir[3].isEmpty()) {
                        locationBuilder.setCounty(sir[3]);
                    }
                    if (!sir[4].isEmpty()) {
                        locationBuilder.setLocality(sir[4]);
                    }
                    if (!sir[5].isEmpty()) {
                        locationBuilder.setAdminUnit(sir[5]);
                    }
                    if (!sir[6].isEmpty()) {
                        locationBuilder.setAddress(sir[6]);
                    }
                    // Creez Location-ul apeland metoda lui de build() care in spate apeleaza
                    // new Location(locationBuilder);
                    Location location = locationBuilder.build();
                    Person manager = getDirectorMuzeu(sir);
                    if (comanda.equals("ADD MUSEUM")) {
                        // pe masura ce vor exista anumite campuri/proprietati optionale extrase din fisier
                        // pe care utilizatorul doreste sa le ataseze obiectului muzeu, le voi seta
                        // prin setteri speciali ai builderului museuzului.
                        Museum.MuseumBuilder museumBuilder = new Museum.MuseumBuilder(denumire, cod, supervisorCode, location);
                        if (!sir[7].isEmpty()) {
                            museumBuilder.setPostalCode(Long.parseLong(sir[7]));
                        }
                        if (!sir[8].isEmpty()) {
                            museumBuilder.setPhoneNumber(sir[8]);
                        }
                        if (!sir[9].isEmpty()) {
                            museumBuilder.setFax(sir[9]);
                        }
                        if (!sir[10].isEmpty()) {
                            museumBuilder.setFoundingYear(Integer.parseInt(sir[10]));
                        }
                        if (!sir[11].isEmpty()) {
                            museumBuilder.setUrl(sir[11]);
                        }
                        if (!sir[12].isEmpty()) {
                            museumBuilder.setEmail(sir[12]);
                        }
                        if (manager != null) {
                            museumBuilder.setManager(manager);
                        }
                        if (!sir[15].isEmpty()) {
                            museumBuilder.setProfile(sir[15]);
                        }
                        if (!sir[16].isEmpty()) {
                            museumBuilder.setCategory(sir[17]);
                        }
                        // la final construiesc obiectul de tip Museum si execut comanda de adaugare
                        // a aunui muzeu.
                        Museum museum = museumBuilder.build();
                        ComandaAddMuseum comand = new ComandaAddMuseum(museum);
                        comand.executa();
                        pw.println(museum.toString());
                    }
                }
                // prind apoi exceptiile afisand in fisier mesaj de eroare.
            } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                pw.println("Exception: Data is broken. ## (" + linie + ")");
            }
        }
        br.close();
        pw.close();
    }

    /**
     * Directorul muzeului poate avea un singur NUME, dar mai multe PRENUME, deci trebuie sa stiu
     * exact la creaarea persoanei care e name-ul si surname-ul asa ca am facut aceasta metoda
     * ajutatoare.
     * @param sir Sirul cu numele si mai multe prenume ale directorului.
     * @return obiectul de tip Person pe care l-am creat corespunzator.
     */
    private static Person getDirectorMuzeu(String[] sir) {
        String[] dateManager = sir[13].split(" ");
        // daca nu e dat niciun Nume prenume1 prenume2 etc, etc, voi returna null
        // ca sa stiu pentru builder pt campul optional sa nu il mai pun.
        if (sir[13].isEmpty()) {
            return null;
        }
        // Construiesc managerul de tip Persoana, din Stringul manager de pe poz.13.
        String surnameManager = "";
        for (int i = 0; i < dateManager.length - 1; i++) {
            surnameManager = surnameManager.concat(dateManager[i]);
        }
        String nameManager = dateManager[dateManager.length - 1];
        return new Person(surnameManager, nameManager, "manager");
    }
}

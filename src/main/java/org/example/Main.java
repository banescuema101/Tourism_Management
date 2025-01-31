package org.example;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Resetez baza de date pentru a face ca datele sa NU fie persistente DECAT pe parcursul unei
        // sesiuni.
        Database.Instanta().resetAll();
        // calea de inceput pe care stiu mereu ca o va avea fisierul
        String antet = "/src/main/resources/";
        // cazul in care args va avea doar 2 parametrii, tipul pathului si calea fisierului.
        if (args.length == 2) {
            String path = args[0];
            // am observat ca testerul creeaza calea fara extensia .in, asa ca o voi concatena.
            String filename = args[1] + ".in";
            try {
                // incerc sa deschid fisierul de intrare folosind File, apoi un FileReader construit pe baza
                // fisierului File (creat avand doar numele dorit filename) si apoi folosesc BufferedReader

                // la crearea fisierului de output, m-am ajutat de
                File inputFile = new File(filename);
                FileReader fr = new FileReader(inputFile);
                BufferedReader br = new BufferedReader(fr);
                // creez fisierul de iesire, functia de mai jos returnand un printWriter
                // prin care voi referi la fisierul creat.
                PrintWriter pwFile1 = creeazaFisier(inputFile);

                // diferentierea fluxului de executie pe baza path type-ului decid in ce mod vor fi
                // parsate liniile din fisierul de intrare curent.
                if (args[0].equals(PathTypes.MUSEUMS.getValue())) {
                    ParsareFisierMuzeu.parsareLiniiMuseum(br, pwFile1);
                    pwFile1.close();
                } else if (args[0].equals(PathTypes.GROUPS.getValue())) {
                    ParsareFisierGroups.parsareLiniiGroups(br, pwFile1);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Fisierul nu a fost gasit: " + filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (args.length == 4) {
            // In caz ca args are 4 parametrii: tipul pathului si cele 3 fisiere de muzeuri,
            // grupuri si events:
            // creez cele 3 File -uri
            String path = args[0];
            String numeFisierMuzee = args[1] + ".in";
            File fisierMuzee = new File(numeFisierMuzee);

            String numeFisierGrupuri = args[2] + ".in";
            File fisierGrupuri = new File(numeFisierGrupuri);

            String numeFisierEvenimente = args[3] + ".in";
            File fisierEvenimente = new File(numeFisierEvenimente);
            try {
                // PENTRU MUZEURI:
                FileReader fr = new FileReader(fisierMuzee);
                BufferedReader brMuzeu = new BufferedReader(fr);
                PrintWriter pwMuzeu = creeazaFisier(fisierMuzee);

                // PENTRU GRUPURI:
                FileReader frGrup = new FileReader(fisierGrupuri);
                BufferedReader brGrup = new BufferedReader(frGrup);
                PrintWriter pwGrupuri = creeazaFisier(fisierGrupuri);

                // PENTRU EVENIMENTE:
                FileReader frEvent = new FileReader(fisierEvenimente);
                BufferedReader brEvent = new BufferedReader(frEvent);
                PrintWriter pwEvent = creeazaFisier(fisierEvenimente);
                // parsez datele din cele 3 fisiere de input:
                if (args[0].equals(PathTypes.LISTENER.getValue())) {
                    ParsareFisierMuzeu.parsareLiniiMuseum(brMuzeu, pwMuzeu);
                    ParsareFisierGroups.parsareLiniiGroups(brGrup, pwGrupuri);
                    ParsareFisierEvenimente.parsareLiniiEvenimente(brEvent, pwEvent);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Eroare la gasirea fisierelor");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Avand la dispozitie un fisier File creat dintr-un nume efectiv String de fisier,
     * scopul este mai intai sa ii modific numele, prin determinarea directorului parinte
     * cu ajutorul metodei: {@link File#getParent()} pe care il voi concatena cu separatorul
     * de fisier, cu numele fara extensie ( fiinda numele contine .in si vreau sa il schimb la .out) si cu
     * extensia .out. Apoi, voi crea FileWriterul pe numele modificat.
     * @param fisier fisierul caruia vreau sa ii returnez un PrintWriter corespunzator.
     * @return PrintWriter-ul corespunzator fisierului de scriere creat.
     * @throws IOException in caz ca apare o exceptie
     */
    private static PrintWriter creeazaFisier(File fisier) throws IOException {
        String parentDirectory = fisier.getParent();
        String fileNameFaraExtensie = fisier.getName().split("\\.")[0];
        FileWriter fw = new FileWriter((parentDirectory + File.separator + fileNameFaraExtensie + ".out"), true);
        return new PrintWriter(fw);
    }

}

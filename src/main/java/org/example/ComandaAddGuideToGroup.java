package org.example;

import java.io.PrintWriter;
import java.util.Set;

public class ComandaAddGuideToGroup extends Comanda{
    private Group grup;
    private Person ghid;
    private PrintWriter pw;
    public ComandaAddGuideToGroup(Group grup, Person ghid, PrintWriter pw) {
        this.grup = grup;
        this.ghid = ghid;
        this.pw = pw;
    }
    @Override
    public void executa() {
        Set<Group> listaGrupuri = Database.Instanta().getListaGrupuri();
        try {
            // trebuie sa actualizez ,,ghidul grupului" si in baza de date!! de asta fac aici.
            if (listaGrupuri.contains(grup)) {
//                listaGrupuri.remove(grup);
                grup.addGuide(ghid);
                // cred ca trebuie sa fac cumva sa nu mai elimin si dupa sa modific si dupa sa bag la loc
                // ca eu cred ca mi se modifica ordinea in Set.
//                listaGrupuri.add(grup);
                pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## new guide: " + grup.getGuide().toString());
                // acum vreau sa inregistrez ghidul la muzeul cu codul MuseumCode
                Museum muzeuPentruAtasareObs = Database.Instanta().findMuseum(grup.getMuseumCode());
                if (muzeuPentruAtasareObs != null) {
                    muzeuPentruAtasareObs.attachObserver((Professor) ghid);
                }
            }
        } catch ( GuideTypeException e) {
            pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + ghid.toString() + ")");
//            listaGrupuri.add(grup);
        } catch (GuideExistsException e) {
            // aici afisez new guide: ghidul ce exista deja
            pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + grup.getGuide().toString() + ")");
        }
    }
}

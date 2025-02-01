package org.example;

import java.io.PrintWriter;
import java.util.Set;

/**
 * Comanda cu rol de adaugare a unui ghid la un grup.
 */
public class ComandaAddGuideToGroup extends Comanda{
    private final Group grup;
    private final Person ghid;
    private final PrintWriter pw;

    /**
     * Constructorul comenzii specifice de adaugare a unui ghid in grup.
     * @param grup Grupul caruia vreau sa ii asignez ghidul.
     * @param ghid Ghidul pe care vreau sa il adaug.
     * @param pw PrintWriterul unde voi scrie rezultatul comenzii (in caz de succes/esec)
     */
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
                grup.addGuide(ghid);
                // Initial, ma gandisem sa elimin grupul si apoi sa il modific si apoi sa il reintriduc din nou,
                // dar asta am observat ca imi schimba ordinea in set si ducea la probleme.
                pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## new guide: " + grup.getGuide().toString());
                // acum am vrut sa inregistrez ghidul la muzeul cu codul MuseumCode
                Museum muzeuPentruAtasareObs = Database.Instanta().findMuseum(grup.getMuseumCode());
                if (muzeuPentruAtasareObs != null) {
                    muzeuPentruAtasareObs.attachObserver((Professor) ghid);
                }
            }
        } catch (GuideTypeException e) {
            pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + ghid.toString() + ")");
        } catch (GuideExistsException e) {
            // aici afisez new guide: ghidul ce exista deja
            pw.println(grup.getMuseumCode() + " ## " + grup.getTimetable() + " ## " + e.getMessage() + " ## (new guide: " + grup.getGuide().toString() + ")");
        }
    }
}

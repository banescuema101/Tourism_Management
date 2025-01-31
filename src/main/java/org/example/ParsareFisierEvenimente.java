package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ParsareFisierEvenimente {
    public static void parsareLiniiEvenimente(BufferedReader brEvent, PrintWriter pwEvent) throws IOException {
        String linie;
        brEvent.readLine();
        // citesc linie cu linie
        while ((linie = brEvent.readLine()) != null) {
            String[] params = linie.split("\\|");
            String comanda = params[0];
            long codEntitate = Long.parseLong(params[1]);
            String mesaj = params[2];
            /*
             * Cand vad comanda ADD EVENT inseamna ca trebuie ca muzeul cu codul codEntitate pe care il voi
             * cauta intai in instanta de baze de date, va trebui sa seteze un eveniment, sa notifice fiecare
             * observator (ghizi din grupuri) atasati la muzeul respectiv, faptul ca un eveniment a avut loc.
             * deci apelez metoda {@link Museum#setEvent(String, PrintWriter)}
             */
            if (comanda.equals("ADD EVENT")) {
                Database.Instanta().findMuseum(codEntitate).setEvent(mesaj, pwEvent);
            }
        }
        pwEvent.close();
        brEvent.close();
    }
}

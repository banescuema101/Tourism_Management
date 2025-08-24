package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
* The class where I effectively read data from an event-type file, and I will practically
* call only the setEvent method corresponding to the museum with the codEntitate code, which in turn
* will display the corresponding notifications in the events.out file.
*/
public class EventsFileParser {
    public static void rowsParser(BufferedReader brEvent, PrintWriter pwEvent) throws IOException {
        String line;
        brEvent.readLine();
        // I read line by line, while there are lines to read
        while ((line = brEvent.readLine()) != null) {
            String[] params = line.split("\\|");
            String command = params[0];
            long entityCode = Long.parseLong(params[1]);
            String message = params[2];
            /*
            * When I see the command ADD EVENT, it means that the museum with the entityCode, which I will
            * first search for in the database instance, will need to set an event and notify each
            * observer (guides from groups) attached to that museum that an event has occurred.
            * Therefore, I call the method {@link Museum#setEvent(String, PrintWriter)}.
            */
            if (command.equals("ADD EVENT")) {
                Database.Instance().findMuseum(entityCode).setEvent(message, pwEvent);
            }
        }
        pwEvent.close();
        brEvent.close();
    }
}

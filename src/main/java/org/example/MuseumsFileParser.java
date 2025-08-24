package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
* The class where I effectively read data from a museum-type file and display the corresponding output.
* I execute commands based on what is in the first column of each line (except the first one)
* and create specific commands with the corresponding parameters retrieved, then execute them.
*/
public class MuseumsFileParser {
    public static void rowsParser(BufferedReader br, PrintWriter pw) throws IOException {
        // nu e recomandat sa setez toate argumentele optionale, daca nu le primesc pe toate.
        String line;
        br.readLine();
        while((line = br.readLine()) != null) {
            try {
                String[] words = line.split("\\|");
                // Split the row into words based on the symbol "|" which is
                // the delimiter in the input file.
                String command = words[0];
                long code = Long.parseLong(words[1]);
                String museumName = words[2];
                String county = words[3];
                long supervisorCode = Long.parseLong(words[14]);
                Integer sirutaCode = Integer.parseInt(words[16]);
                // I check to avoid parsing a null "" value, which would incorrectly indicate
                // that the optional attribute should be set, but it shouldn't actually be.
                Location.LocationBuilder locationBuilder = new Location.LocationBuilder(county, sirutaCode);
                // I had to replace the comma with an empty character, because for example,
                // the value latitude = 412,546 had to be taken as 412546 to be a valid
                // latitude/longitude value.
                if (!words[18].isEmpty()) {
                    int latitude = Integer.parseInt(words[18].replace(",", ""));
                    locationBuilder.setLatitude(latitude);
                }
                if (!words[19].isEmpty()) {
                    int longitude = Integer.parseInt(words[19].replace(",", ""));
                    locationBuilder.setLongitude(longitude);
                }
                if (words[0].equals("ADD MUSEUM")) {
                    if (!words[3].isEmpty()) {
                        locationBuilder.setCounty(words[3]);
                    }
                    if (!words[4].isEmpty()) {
                        locationBuilder.setLocality(words[4]);
                    }
                    if (!words[5].isEmpty()) {
                        locationBuilder.setAdminUnit(words[5]);
                    }
                    if (!words[6].isEmpty()) {
                        locationBuilder.setAddress(words[6]);
                    }
                    // Create the Location by calling its build() method, which internally calls
                    // new Location(locationBuilder);
                    Location location = locationBuilder.build();
                    Person manager = getMuseumDirector(words);
                    if (command.equals("ADD MUSEUM")) {
                    // as certain optional fields/properties are extracted from the file,
                    // which the user wants to attach to the museum object, I will set them
                    // using the special setters of the museum builder.
                        Museum.MuseumBuilder museumBuilder = new Museum.MuseumBuilder(museumName, code, supervisorCode, location);
                        if (!words[7].isEmpty()) {
                            museumBuilder.setPostalCode(Long.parseLong(words[7]));
                        }
                        if (!words[8].isEmpty()) {
                            museumBuilder.setPhoneNumber(words[8]);
                        }
                        if (!words[9].isEmpty()) {
                            museumBuilder.setFax(words[9]);
                        }
                        if (!words[10].isEmpty()) {
                            museumBuilder.setFoundingYear(Integer.parseInt(words[10]));
                        }
                        if (!words[11].isEmpty()) {
                            museumBuilder.setUrl(words[11]);
                        }
                        if (!words[12].isEmpty()) {
                            museumBuilder.setEmail(words[12]);
                        }
                        if (manager != null) {
                            museumBuilder.setManager(manager);
                        }
                        if (!words[15].isEmpty()) {
                            museumBuilder.setProfile(words[15]);
                        }
                        if (!words[16].isEmpty()) {
                            museumBuilder.setCategory(words[17]);
                        }
                        // Finally, I build the Museum object and execute the add museum command.
                        Museum museum = museumBuilder.build();
                        CommandAddMuseum commandAddMuseum = new CommandAddMuseum(museum);
                        commandAddMuseum.execute();
                        pw.println(museum.toString());
                    }
                }
                // catching the exceptions that can be thrown by the parsing methods.
            } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                pw.println("Exception: Data is broken. ## (" + line + ")");
            }
        }
        br.close();
        pw.close();
    }

    /**
    * The museum director can have only one LAST NAME, but multiple FIRST NAMES, so I need to know
    * exactly when creating the person which is the first name and which is the last name, so I created this helper method.
    * @param row I take from the row, the array containing the last name and multiple first names of the director.
    * @return The Person object that I created accordingly.
    */
    private static Person getMuseumDirector(String[] row) {
        String[] managerData = row[13].split(" ");
        // If no Last Name, First Name1, First Name2, etc., etc., is provided, I will return null
        // so that I know not to include it in the builder for the optional field.
        if (row[13].isEmpty()) {
            return null;
        }
        // I build the manager as a Person object, knowing his surname and his first name.
        String surnameManager = "";
        for (int i = 0; i < managerData.length - 1; i++) {
            surnameManager = surnameManager.concat(managerData[i]);
        }
        String nameManager = managerData[managerData.length - 1];
        return new Person(surnameManager, nameManager, "manager");
    }
}

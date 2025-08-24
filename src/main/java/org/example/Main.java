package org.example;
import java.io.*;

/**
* The main class where I create the input and output files, calling the methods from the corresponding
* parsing classes (the parsers also handle each first argument on each line except the first one,
* creating and executing each corresponding command.)
*/
public class Main {
    public static void main(String[] args) {
        // Resetting the database to make data is NOT persistent EXCEPT during a session.
        Database.Instance().resetAll();

        // the case where args will have only 2 parameters, the path type and the file path.
        if (args.length == 2) {
            // The tester creates the path without the .in extension, so I will concatenate it.
            String filename = args[1] + ".in";
            try {
                // I try to open the input file using File, then a FileReader built on the basis
                // of the File object (created with only the desired filename) and then use BufferedReader
                File inputFile = new File(filename);
                FileReader fr = new FileReader(inputFile);
                BufferedReader br = new BufferedReader(fr);
                // I create the output file, the function below returns a PrintWriter
                // through which I will refer to the created file.
                PrintWriter pwFile1 = createFile(inputFile);

                // Differentiating the execution flow based on the path type to decide how
                // the lines in the current input file will be parsed.
                if (args[0].equals(PathTypes.MUSEUMS.getValue())) {
                    MuseumsFileParser.rowsParser(br, pwFile1);
                    pwFile1.close();
                } else if (args[0].equals(PathTypes.GROUPS.getValue())) {
                    GroupsFileParser.parsareLiniiGroups(br, pwFile1);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File wasn't found " + filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (args.length == 4) {
            // In case args has 4 parameters: the path type and the 3 files for museums,
            // groups, and events:
            // I create the 3 File objects
            String museumsFileName = args[1] + ".in";
            File museumFile = new File(museumsFileName);

            String groupsFileName = args[2] + ".in";
            File groupsFile = new File(groupsFileName);

            String eventsFileName = args[3] + ".in";
            File eventsFile = new File(eventsFileName);
            try {
                // for MUSEUMS:
                FileReader fr = new FileReader(museumFile);
                BufferedReader brMuseum = new BufferedReader(fr);
                PrintWriter pwMuseum = createFile(museumFile);

                // for GROUPS:
                FileReader frGroup = new FileReader(groupsFile);
                BufferedReader brGroup = new BufferedReader(frGroup);
                PrintWriter pwGroups = createFile(groupsFile);

                // for EVENTS:
                FileReader frEvent = new FileReader(eventsFile);
                BufferedReader brEvent = new BufferedReader(frEvent);
                PrintWriter pwEvent = createFile(eventsFile);
                // Differentiating the parsing flow based on the path type to decide how
                if (args[0].equals(PathTypes.LISTENER.getValue())) {
                    MuseumsFileParser.rowsParser(brMuseum, pwMuseum);
                    GroupsFileParser.parsareLiniiGroups(brGroup, pwGroups);
                    EventsFileParser.rowsParser(brEvent, pwEvent);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Files weren't found ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
    * Given a File object created from an actual String file name,
    * the goal is first to modify its name by determining the parent directory
    * using the method: {@link File#getParent()}, which will be concatenated with the file separator,
    * the name without the extension (since the name contains .in and I want to change it to .out), and
    * the .out extension. Then, I will create the FileWriter with the modified name.
    * @param file the file for which I want to return a corresponding PrintWriter.
    * @return the corresponding PrintWriter for the created output file.
    * @throws IOException in case an exception occurs
    */
    private static PrintWriter createFile(File file) throws IOException {
        String parentDirectory = file.getParent();
        String fileNameWithoutExtension = file.getName().split("\\.")[0];
        FileWriter fw = new FileWriter((parentDirectory + File.separator + fileNameWithoutExtension + ".out"), true);
        return new PrintWriter(fw);
    }

}

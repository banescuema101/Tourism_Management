package org.example;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // nu sunt sigura aici daaca trebuie sau nu resetat.
        Database.Instanta().resetAll();
        String antet = "/src/main/resources/";
        if (args.length == 2) {
            String path = args[0];
            String filename = args[1] + ".in";
            try {
                File inputFile = new File(filename);
                FileReader fr = new FileReader(inputFile);
                BufferedReader br = new BufferedReader(fr);
                String parentDirectory = inputFile.getParent();
                String fileName = inputFile.getName().split("\\.")[0];

                String outputPathFile1 = parentDirectory + File.separator + fileName + ".out";
                FileWriter fwFile1 = new FileWriter(outputPathFile1, true);
                PrintWriter pwFile1 = new PrintWriter(fwFile1);
                if (args[0].equals(PathTypes.MUSEUMS.getValue())) {
                    parsareLiniiMuseum(br, pwFile1);
                    fwFile1.close();
                } else if (args[0].equals(PathTypes.GROUPS.getValue())) {
                    parsareLiniiGroups(br, pwFile1);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (args.length == 4) {
            String path = args[0];
            String numeFisierMuzee = args[1] + ".in";
            File fisierMuzee = new File(numeFisierMuzee);

            String numeFisierGrupuri = args[2] + ".in";
            File fisierGrupuri = new File(numeFisierGrupuri);

            String numeFisierEvenimente = args[3] + ".in";
            File fisierEvenimente = new File(numeFisierEvenimente);
            try {
                // PENTRU MUZEU.
                FileReader fr = new FileReader(fisierMuzee);
                BufferedReader brMuzeu = new BufferedReader(fr);
                // directorul radacina al fisierelor, il aleg pe cel de muzee, dar evident, e acelasi pentru toate file-urile
                // din directorul generat de MainTest.
                String parentDirectory = fisierMuzee.getParent();
                String fileNameFaraExtensie = fisierMuzee.getName().split("\\.")[0];
                FileWriter fw = new FileWriter((parentDirectory + File.separator + fileNameFaraExtensie + ".out"), true);
                PrintWriter pwMuzeu = new PrintWriter(fw);
                // GRUPURI:

                FileReader frGrup = new FileReader(fisierGrupuri);
                BufferedReader brGrup = new BufferedReader(frGrup);
                String fileGrupuriNameFaraExtensie = fisierGrupuri.getName().split("\\.")[0];
                FileWriter fwGrupuri = new FileWriter((parentDirectory + File.separator + fileGrupuriNameFaraExtensie + ".out"), true);
                PrintWriter pwGrupuri = new PrintWriter(fwGrupuri);

                // evenimente:
                FileReader frEvent = new FileReader(fisierEvenimente);
                BufferedReader brEvent = new BufferedReader(frEvent);
                String fileEventNameFaraExtensie = fisierEvenimente.getName().split("\\.")[0];
                FileWriter fwEvent = new FileWriter((parentDirectory + File.separator + fileEventNameFaraExtensie + ".out"), true);
                PrintWriter pwEvent = new PrintWriter(fwEvent);
                parsareLiniiMuseum(brMuzeu, pwMuzeu);
                parsareLiniiGroups(brGrup, pwGrupuri);
                parsareLiniiEvenimente(brEvent, pwEvent);

            } catch (FileNotFoundException e) {
                System.out.println("One of the files not found");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
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
    private static void parsareLiniiMuseum(BufferedReader br, PrintWriter pw) throws IOException{
        // nu e recomandat sa setez toate argumentele optionale, daca nu le primesc pe toate.
        String linie;
        br.readLine();
        while((linie = br.readLine()) != null) {
            try {
                String[] sir = linie.split("\\|");
                String comanda = sir[0];
                long cod = Long.parseLong(sir[1]);
                String denumire = sir[2];
                String county = sir[3];
                long supervisorCode = Long.parseLong(sir[14]);
                Integer sirutaCode = Integer.parseInt(sir[16]);
                // verific sa nu parsez o valoare "" nula practic, si apoi voi avea
                // in mod eronat ca ar trebui pus atributul optional, dar nu trebuie defapt.
                Location.LocationBuilder locationBuilder = new Location.LocationBuilder(county, sirutaCode);
                if (!sir[18].isEmpty()) {
                    int latitude = Integer.parseInt(sir[18].replace(",", ""));
                    locationBuilder.setLatitude(latitude);
                }
                if (!sir[19].isEmpty()) {
                    int longitude = Integer.parseInt(sir[19].replace(",", ""));
                    locationBuilder.setLongitude(longitude);
                }
                if (sir[0].equals("ADD MUSEUM")) {
                    if (!sir[5].isEmpty()) {
                        locationBuilder.setAdminUnit(sir[5]);
                    }
                    if (!sir[6].isEmpty()) {
                        locationBuilder.setAddress(sir[6]);
                    }
                    Location location = locationBuilder.build();
                    Person manager = getDirectorMuzeu(sir);
                    if (comanda.equals("ADD MUSEUM")) {
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
                        Museum museum = museumBuilder.build();
                        ComandaAddMuseum comand = new ComandaAddMuseum(museum);
                        comand.executa();
                        pw.println(museum.toString());
                    }
                }
            } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                pw.println("Exception: Data is broken. ## (" + linie + ")");
            }
        }
        br.close();
        pw.close();
    }
    private static void parsareLiniiGroups(BufferedReader br, PrintWriter pw) throws IOException {
        String linie;
        br.readLine();
        while((linie = br.readLine()) != null) {
            int nr = 0;
            String[] sir = linie.split("\\|");
            String comanda = sir[0];
            String prenume = sir[1];
            String nume = sir[2];
            String titulatura = sir[3];
            // daca nu gasesc termenul
            //in csv file, atunci il pun default pe o valoare imposibila: -1.
            int varsta = -1;
            if (!sir[4].isEmpty()) {
                varsta = Integer.parseInt(sir[4]);
            }
            String email = sir[5];
            if (email.isEmpty()) {
                email = "null";
            }
            String scoala = sir[6];
            int anStudiiSauExperienta = -1;
            if(!sir[7].isEmpty()) {
                anStudiiSauExperienta = Integer.parseInt(sir[7]);
            }
            String rol = sir[8];
            Person persoanaParsata = null;
            if (titulatura.equals("student")) {
                persoanaParsata = new Student(prenume, nume, titulatura, scoala, anStudiiSauExperienta);
            } else {
                persoanaParsata = new Professor(prenume, nume, titulatura, anStudiiSauExperienta, scoala);
            }
            persoanaParsata.setAge(varsta);
            persoanaParsata.setEmail(email);
            persoanaParsata.setRole(rol);
            int codeMuseum = -1;
            if (!sir[9].isEmpty()) {
                codeMuseum = Integer.parseInt(sir[9]);
            }
            String timetable =  sir[10];
            // daca NU EXISTA UN MAIN GROUP:
            if (comanda.equals("ADD GUIDE")) {
                nr++;
                Group grup = new Group(codeMuseum, timetable);
                Group grupGasit = Database.Instanta().findGroup(codeMuseum, timetable);
                System.out.println("alala" + nr + " " + timetable);
                System.out.println(grupGasit != null?"da":"nu");
                if (grupGasit != null) {
                    ComandaAddGuideToGroup comandaAddGuide = new ComandaAddGuideToGroup(grupGasit, persoanaParsata, pw);
                    comandaAddGuide.executa();
                } else {
                    if (persoanaParsata instanceof Professor) {
                        ComandaAddGroup comandaAddGroup = new ComandaAddGroup(grup);
                        // aici e ceva ca il creez cu ghidul si apoi vreau sa il adaug si da exceptie ca deja il am EVIDENT.
                        comandaAddGroup.executa();
                        System.out.println(grup.getGuide());
                        ComandaAddGuideToGroup comandaAddGuide = new ComandaAddGuideToGroup(grup, persoanaParsata, pw);
                        comandaAddGuide.executa();
                        }
                    }
            } else if (comanda.equals("ADD MEMBER")) {
                try {
                    System.out.println("TEST TEST 4:" + codeMuseum + " " + timetable);
                    ComandaAddMember comandaAddMember = new ComandaAddMember(codeMuseum, timetable, persoanaParsata, pw);
                    comandaAddMember.executa();
                } catch (GroupNotExistsException e) {
                    pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (new member: " + persoanaParsata + ")");
                }
            } else if (comanda.equals("REMOVE GUIDE")) {
                    ComandaRemoveGuide comandaRemoveGuide = new ComandaRemoveGuide(codeMuseum, timetable, pw);
                    try {
                        comandaRemoveGuide.executa();
                    } catch (GroupNotExistsException e) {
                        pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + "## (removed member: " + comandaRemoveGuide.getGhidDeResetat().toString() + ")");
                    }
            } else if (comanda.equals("REMOVE MEMBER")) {
                ComandaRemoveMember comandaRemoveMember = new ComandaRemoveMember(codeMuseum, timetable, persoanaParsata, pw);
                try {
                    comandaRemoveMember.executa();
                } catch (GroupNotExistsException e) {
                    pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (removed member: " + persoanaParsata + ")");
                }
            } else if (comanda.equals("FIND GUIDE")) {
                ComandaFindGuide comandaFindGuide = new ComandaFindGuide(codeMuseum, timetable, persoanaParsata, pw);
                try {
                    comandaFindGuide.executa();
                } catch (GroupNotExistsException e) {
                    pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (find guide: " + persoanaParsata + ")");
                }
            } else if (comanda.equals("FIND MEMBER")) {
                ComandaFindMember comandaFindMember = new ComandaFindMember(codeMuseum, timetable, persoanaParsata, pw);
                try {
                    comandaFindMember.executa();
                } catch (GroupNotExistsException e) {
                    pw.println(codeMuseum + " ## " + timetable + " ## " + e.getMessage() + " ## (find member: " + persoanaParsata + ")");
                }
            }
        }
        br.close();
        pw.close();
    }
    private static void parsareLiniiEvenimente(BufferedReader brEvent, PrintWriter pwEvent) throws IOException {
        String linie;
        brEvent.readLine();
        while ((linie = brEvent.readLine()) != null) {
            String[] params = linie.split("\\|");
            String comanda = params[0];
            long codEntitate = Long.parseLong(params[1]);
            String mesaj = params[2];
            if (comanda.equals("ADD EVENT")) {
                // intai trebuie sa trimita muzeul de arta in ultimul test, si apoi muzeul george calinescu.
                Database.Instanta().findMuseum(codEntitate).setEvent(mesaj, pwEvent);
            }
        }
        pwEvent.close();
        brEvent.close();
    }
}

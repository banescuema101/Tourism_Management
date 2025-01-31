package org.example;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Museum {
    private List<Professor> observers = new ArrayList<Professor>();
    // campuri obligatorii
    private String name;
    private long code;
    private long supervisorCode; // codul institutiei supervisoare.
    private Location location;
    // capurile optionale
    private Person manager;  // a mean directorul
    private Integer foundingYear;
    private String phoneNumber;
    private String fax;
    private String email;
    private String url;
    private String profile;
    // codul postal. Aici le am pus eu ce mi s a parut ca mai
    // trebuie optional.. nu stiu sigur.
    private long postalCode;
    private String category;
    private String coordonate;
    // coordonatele : museum


    public String getName() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public long getSupervisorCode() {
        return supervisorCode;
    }

    public Location getLocation() {
        return location;
    }

    public Person getManager() {
        return manager;
    }

    public Integer getFoundingYear() {
        return foundingYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getProfile() {
        return profile;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public String getCategory() {
        return category;
    }

    public String getCoordonate() {
        return coordonate;
    }

    // si altele..
    // Clasa Builder pentru constructia
    public static class MuseumBuilder {
        // campuri obligatorii
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;
        // capurile optionale
        private Person manager;
        private Integer foundingYear;
        private String phoneNumber;
        private String fax;
        private String email;
        private String url;
        private String profile;
        private long postalCode;
        private String category;
        private String coordonate;
        public MuseumBuilder(String name, long code, long supervisorCode, Location location) {
            this.name = name;
            this.code = code;
            this.supervisorCode = supervisorCode;
            this.location = location;
        }
        public MuseumBuilder setManager(Person manager) {
            this.manager = manager;
            // si returnez referinta actuala.
            return this;
        }
        public MuseumBuilder setFoundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }
        public MuseumBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public MuseumBuilder setFax(String fax) {
            this.fax = fax;
            return this;
        }
        public MuseumBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public MuseumBuilder setUrl(String url) {
            this.url = url;
            return this;
        }
        public MuseumBuilder setProfile(String profile) {
            this.profile = profile;
            return this;
        }
        public MuseumBuilder setPostalCode(long postalCode) {
            this.postalCode = postalCode;
            return this;
        }
        public MuseumBuilder setCategory(String category) {
            this.category = category;
            return this;
        }
        public MuseumBuilder setCoordonate(String coordonate) {
            this.coordonate = coordonate;
            return this;
        }
        public Museum build() {
            return new Museum(this);
        }
    }
    private Museum(MuseumBuilder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.supervisorCode = builder.supervisorCode;
        this.location = builder.location;
        this.manager = builder.manager;
        this.foundingYear = builder.foundingYear;
        this.phoneNumber = builder.phoneNumber;
        this.fax = builder.fax;
        this.email = builder.email;
        this.url = builder.url;
        this.profile = builder.profile;
        this.postalCode = builder.postalCode;
        this.category = builder.category;
        this.coordonate = builder.coordonate;
    }
    public String toString() {
        return this.code + ": " + this.name;
    }

    /**
     * Metoda de atasare a unui observator, adica pur si simplu il inserez in lista de observers.
     * Aceasta metoda o voi folosi in cadrul metodei: {@link ComandaAddGuideToGroup#executa()}. Pentru ca
     * atunci cand adaug un ghid unui grup, si operatia se finalizeaza cu succes, nu apar exceptii, voi
     * atasa acel ghid (de tip Professor) muzeului la care grupul are vizita, cu codul codeMuseum.
     * @param observer Ghidul pe care doresc sa il atasez in lista de "observatori" ce apartin acestui MUZEU.
     */
    public void attachObserver(Professor observer) {
        observers.add(observer);
    }

    /**
     *
     * @param observer Observatorul pe care vreau sa il notific.
     * @param message Mesajul pe care vreau sa i-l trimit.
     * @param pw PrintWriterul cu ajutorul caruia {@link Professor#update(String, PrintWriter)} va afisa mailul in
     *           fisierul event.out.
     */
    public void notifyObserver(Professor observer, String message, PrintWriter pw) {
            observer.update(message, pw);
    }

    /**
     * Un setter in care practic, imi actualizez starea subiectului, adica a muzeului, in starea
     * de trimitere a unui mesaj. Iterez prin fiecare observator (ghid de tip Professor), formez mesajul
     * care contine emailul observatorului respectiv, concatenat cu numele MUZEULUI si codul MUZEULUI.
     * si aplic metoda de notificare a observatorului la care am ajuns.{@link Museum#notifyObserver(Professor, String, PrintWriter)}
     * @param message mesajul pe care doreste muzeul, prin ulterioara comanda de adaugare Eveniment,
     *                pe care il voi parsa in metoda {@link ParsareFisierEvenimente#parsareLiniiEvenimente(BufferedReader, PrintWriter)}.
     * @param pw printWriterul pe care il va primi ca parametru notifyObserver. Ii trimitem mailul personalizat ulterior si
     *           conform cerintei, il voi scrie in fisierul event.out.
     */
    public void setEvent(String message, PrintWriter pw) {
        System.out.println("Am intrat in setEvent");
        for (Professor observer : observers) {
            System.out.println(observer);
            String mesajMail = "To: " + observer.getEmail() + " ## Message: " + this.name + " (" + this.code + ")" + " " + message;
            notifyObserver(observer, mesajMail, pw);
        }
    }
}

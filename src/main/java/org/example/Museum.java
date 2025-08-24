package org.example;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
* Class that models the museum entity, with a list of attached observers of
* type Professor, mandatory and optional parameters. (builder design pattern)
*/
public class Museum {
    private List<Professor> observers = new ArrayList<Professor>();
    // about the number of visited groups.
    // mandatory fields
    private String name;
    private long code;
    private long supervisorCode;
    private Location location;
    // optional fields
    private Person manager;  // the museum director basically.
    private Integer foundingYear;
    private String phoneNumber;
    private String fax;
    private String email;
    private String url;
    private String profile;

    // In case I will need to use these fields later.
    private long postalCode;
    private String category;
    private String coordonate;


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

    // Builder Design Pattern -> builder class inside the Museum class.
    public static class MuseumBuilder {
        // mandatory fields
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;
        // optional fields
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
            // and I will return a reference to the current instance of the builder class.
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

    // Below are some methods specific to the Observer design pattern,
    // Considering the Museum as the subject that, for each event (ADD EVENT command from EventsFileParser),
    // will notify the attached observers (registered to the museum) by sending an email (simulated and reflected
    // in the application logic by displaying those emails in the events...out file.

    /**
     * Method to attach an observer, meaning I simply insert it into the list of observers.
     * This method will be used in the method: {@link CommandAddGuideToGroup#execute()}. Because
     * when I add a guide to a group, and the operation is successfully completed without exceptions, I will
     * attach that guide (of type Professor) to the museum where the group has a visit, with the code codeMuseum.
     * @param observer The guide I want to attach to the list of "observers" belonging to this MUSEUM.
     */
    public void attachObserver(Professor observer) {
        observers.add(observer);
    }

    /**
    *
    * @param observer The observer I want to notify.
    * @param message The message I want to send to them.
    * @param pw The PrintWriter used by {@link Professor#update(String, PrintWriter)} to display the email in
    *           the event.out file.
    */
    public void notifyObserver(Professor observer, String message, PrintWriter pw) {
            observer.update(message, pw);
    }

    /**
    * A setter where I practically update the state of the subject, meaning the museum, to the state
    * of sending a message. I iterate through each observer (guide of type Professor), form the message
    * which contains the email of the respective observer, concatenated with the name of the MUSEUM and the code of the MUSEUM,
    * and apply the notification method for the observer I have reached. {@link Museum#notifyObserver(Professor, String, PrintWriter)}
    * @param message The message the museum wants to send, through the subsequent add Event command,
    *                which I will parse in the method {@link EventsFileParser#rowsParser(BufferedReader, PrintWriter)}.
    * @param pw The PrintWriter that will be passed as a parameter to notifyObserver. We send the personalized email later and,
    *           as per the requirements, I will write it in the event.out file.
    */
    public void setEvent(String message, PrintWriter pw) {
//        System.out.println("Am intrat in setEvent");
        for (Professor observer : observers) {
//            System.out.println(observer);
            String mailMessage = "To: " + observer.getEmail() + " ## Message: " + this.name + " (" + this.code + ")" + " " + message;
            notifyObserver(observer, mailMessage, pw);
        }
    }
}

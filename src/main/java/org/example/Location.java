package org.example;

/**
 * Clasa Location definita pentru a gestiona si organiza datele
 * asociate locului in care se afla entitatile muzeale. Va avea atat campuri obligatorii
 * cat si optionale (Design pattern: Builder)
 */
public class Location {
    // campurile obligatorii.
    private String country;
    private Integer sirutaCode;
    // campurile optionale
    private String judet;
    private String locality;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;
    public static class LocationBuilder {
        // campurile obligatorii.
        private String county;
        private Integer sirutaCode;
        // campurile optionale
        private String judet;
        private String locality;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;

        /**
         * Constructor pentru campurile obligatorii.
         * @param country judetul aferent
         * @param sirutaCode codul siruta.
         */
        public LocationBuilder(String country, Integer sirutaCode) {
            this.county = country;
            this.sirutaCode = sirutaCode;
        }
        // mai jos, setari pentru fiecare camp optional, care imi vor intoarce
        // mereu referinta la instanta curenta a clasei in care ma aflu acum.

        public LocationBuilder setLocality(String locality) {
            this.locality = locality;
            return this;
        }
        public LocationBuilder setAdminUnit(String adminUnit) {
            this.adminUnit = adminUnit;
            return this;
        }
        public LocationBuilder setAddress(String address) {
            this.address = address;
            return this;
        }
        public LocationBuilder setLatitude(Integer latitude) {
            this.latitude = latitude;
            return this;
        }
        public LocationBuilder setLongitude(Integer longitude) {
            this.longitude = longitude;
            return this;
        }
        public LocationBuilder setJudet(String judet) {
            this.judet = judet;
            return this;
        }
        // metoda build() a builderului intern clasei Location, care va returna un obiect de tipul Location
        // construit prin constructorul lui mai special ce are ca parametru builderul intern, referit aici
        // prin this, care mereu se va actualiza pe masura ce ulterior in logica aplicatiei
        // setez parametrii optionali, pe care nu stiu din start daca ii va avea sau nu entitatea Location.
        public Location build() {
            return new Location(this);
        }
    }
    private Location(LocationBuilder builder) {
        this.country = builder.county;
        this.sirutaCode = builder.sirutaCode;
        this.locality = builder.locality;
        this.judet = builder.judet;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }
}

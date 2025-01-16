package org.example;

public class Location {
    // campuri obligatorii.
    private String country;
    private Integer sirutaCode;
    // campuri optionale
    private String judet;
    private String locality;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;
    public static class LocationBuilder {
        // campuri obligatorii.
        private String county;
        private Integer sirutaCode;
        // campuri optionale
        private String judet;
        private String locality;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;
        public LocationBuilder(String country, Integer sirutaCode) {
            this.county = country;
            this.sirutaCode = sirutaCode;
        }
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

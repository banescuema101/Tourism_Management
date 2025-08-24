package org.example;

/**
 * The Location class is defined to manage and organize data
 * associated with the place where museum entities are located. It will have both mandatory
 * and optional fields (Design pattern: Builder)
 */
public class Location {
    // mandatory fields
    private String county;
    private Integer sirutaCode;
    // optional fields
    private String locality;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;
    public static class LocationBuilder {
        // mandatory fields
        private String county;
        private Integer sirutaCode;
        // optional fields
        private String locality;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;

        /**
         * Constructor for the mandatory fields.
         * @param country the associated country.
         * @param sirutaCode siruta code.
         */
        public LocationBuilder(String country, Integer sirutaCode) {
            this.county = country;
            this.sirutaCode = sirutaCode;
        }
        // below, setters for each optional field, which will always return
        // a reference to the current instance of the class in which I am now.
        public LocationBuilder setCounty(String county) {
            this.county = county;
            return this;
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
        // The build() method of the internal builder class Location, which will return an object of type Location
        // constructed using its special constructor that takes the internal builder as a parameter, referred to here
        // as this, which will always be updated as I later set the optional parameters in the application logic,
        // parameters that I do not know in advance whether the Location entity will have or not.
        public Location build() {
            return new Location(this);
        }
    }
    private Location(LocationBuilder builder) {
        this.county = builder.county;
        this.sirutaCode = builder.sirutaCode;
        this.locality = builder.locality;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }
}

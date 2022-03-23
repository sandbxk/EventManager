package be;

public class Venue {

    private String venueName;
    private String address;
    private String zipCode;
    private String city;


    public Venue(String venueName, String address, String zipCode, String city) {
        this.venueName = venueName;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
    }


    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

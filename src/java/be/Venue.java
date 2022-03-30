package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.datatransfer.StringSelection;

public class Venue {

    private IntegerProperty id;
    private StringProperty venueName;
    private StringProperty address;
    private StringProperty zipCode;
    private StringProperty city;


    public Venue(int id, String venueName, String address, String zipCode, String city) {
        this.id = new SimpleIntegerProperty();
        this.venueName = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.zipCode = new SimpleStringProperty();
        this.city = new SimpleStringProperty();

        this.id.set(id);
        this.venueName.set(venueName);
        this.address.set(address);
        this.zipCode.set(zipCode);
        this.city.set(city);

    }

    public int getID(){return id.get();};

    public void setID(int id){this.id.set(id);};

    public String getVenueName() {
        return venueName.get();
    }

    public StringProperty getVenueNameProperty(){
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName.set(venueName);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty getAddressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public StringProperty getZipCodeProperty() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty getCityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

}

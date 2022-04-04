package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PriceGroup {

    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty price;
    private StringProperty currency;

    public PriceGroup(int id, String name, int price, String currency) {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleIntegerProperty();
        this.currency = new SimpleStringProperty();

        this.id.set(id);
        this.name.set(name);
        this.price.set(price);
        this.currency.set(currency);
    }

    public int getID(){return id.get();};

    public void setID(int id){this.id.set(id);};

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getCurrency() {
        return currency.get();
    }

    public StringProperty currencyProperty() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency.set(currency);
    }
}

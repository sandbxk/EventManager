package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserInfo
{
    private int id;


    private StringProperty emailProperty;
    private StringProperty nameProperty;
    private IntegerProperty zipCodeProperty;
    private String name;
    private EUserType type;
    private int zipCode;
    private String email;
    public UserInfo(){}

    public UserInfo(int id, String name, EUserType type,int zipCode, String email) {
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
        this.zipCodeProperty = new SimpleIntegerProperty();

        this.id = id;
        this.name = name;
        this.type = type;
        this.zipCode = zipCode;
        this.email = email;

        nameProperty.set(name);
        emailProperty.set(email);
        zipCodeProperty.set(zipCode);
    }


    public UserInfo(int id, String name, EUserType type)
    {
        this(id, name, type,0000, "Example@Address.com");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StringProperty getNameProperty()
    {
        return nameProperty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EUserType getType() {
        return type;
    }

    public void setType(EUserType type) {
        this.type = type;
    }

    public int getZipCode() {
        return zipCode;
    }

    public IntegerProperty getZipCodeProperty(){return zipCodeProperty;}

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public StringProperty getEmailProperty(){ return emailProperty;}

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }


    public EUserType type() {
        return type;
    }
}

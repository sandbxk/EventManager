package be;

public class UserInfo
{
    private int id;



    private String name;
    private EUserType type;
    private int zipCode;
    private String email;
    public UserInfo(){}
    public UserInfo(int id, String name, EUserType type,int zipCode, String email) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.zipCode = zipCode;
        this.email = email;
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

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

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

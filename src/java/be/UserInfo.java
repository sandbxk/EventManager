package be;

public record UserInfo(int id, String name, EUserType type,int zipCode, String email)
{
    public UserInfo()
    {
        this(-1, "Unknown", EUserType.INVALID,0000 , "Example@Address.com");
    }


    public UserInfo(int id, String name, EUserType type)
    {
        this(id, name, type,0000, "Example@Address.com");
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }


}

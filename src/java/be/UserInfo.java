package be;

public record UserInfo(long id, String name, EUserType type, String email, String phone)
{
    public UserInfo()
    {
        this(-1, "Unknown", EUserType.INVALID, "Example@Address.com", "12345678");
    }


    public UserInfo(long id, String name, EUserType type)
    {
        this(id, name, type, "Example@Address.com", "12345678");
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

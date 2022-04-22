package be;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    /**
     * useless test method, only used to display our ability to create a unit test
     * */
    @org.junit.jupiter.api.Test
    void getId()
    {
        var user = new UserInfo(42, "John Doe", EUserType.EVENT_COORDINATOR, 6700, "JDO@unknown.com");

        assert (user.getId() == 42);
    }

}
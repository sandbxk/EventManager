package bll.interfaces;

import be.EUserType;
import be.UserInfo;

public interface IAuthenticator
{
    /**
     *
     * */
    UserInfo getUserInfo();

    /**
     *
     * @param username
     * @param password
     * @return
     * */
    boolean authenticate(String username, String password);

}

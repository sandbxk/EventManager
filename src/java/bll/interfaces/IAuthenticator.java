package bll.interfaces;

import be.EUserType;

public interface IAuthenticator
{
    /**
     *
     * */
    EUserType getUserType();

    /**
     *
     * @param username
     * @param password
     * @return
     * */
    boolean authenticate(String username, String password);
}

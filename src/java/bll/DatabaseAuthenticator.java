package bll;

import be.EUserType;
import be.UserInfo;
import bll.interfaces.IAuthenticator;

public class DatabaseAuthenticator implements IAuthenticator
{
    private UserInfo info;


    @Override
    public UserInfo getUserInfo()
    {
        return info;
    }

    @Override
    public boolean authenticate(String username, String password)
    {
        info = new UserInfo();

        return false;
    }

    private void queryDatabase(String username, String password)
    {

    }
}

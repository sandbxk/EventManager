package bll;

import be.EUserType;
import bll.interfaces.IAuthenticator;

public class DatabaseAuthenticator implements IAuthenticator
{
    private EUserType m_UserType = EUserType.INVALID;

    @Override
    public EUserType getUserType() { return m_UserType; }

    @Override
    public boolean authenticate(String username, String password)
    {
        m_UserType = EUserType.INVALID;

        return false;
    }

    private void queryDatabase(String username, String password)
    {

    }
}

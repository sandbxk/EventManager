package bll;

import be.UserInfo;
import bll.interfaces.IAuthenticator;

public class SessionManager
{
    private final IAuthenticator authenticator;

    private SessionManager()
    {
        authenticator = new DatabaseAuthenticator();
    }

    private static SessionManager backingSession;
    public static SessionManager getCurrent()
    {
        if (backingSession == null)
            backingSession = new SessionManager();

        return backingSession;
    }

    public UserInfo getLoggedInUser()
    {
        return this.authenticator.getUserInfo();
    }

    public static boolean login(String uname, String password)
    {
        return getCurrent().authenticator.authenticate(uname, password);
    }

    public void logout()
    {
        backingSession = null;
    }
}

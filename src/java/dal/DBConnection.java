package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection
{
    public static final String DRIVER_CLASS         = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String CONNECTION_STRING    = "jdbc:sqlserver://";

    public static final String USERNAME = "";
    public static final String PASSWORD = "";


    public static Connection get() throws ClassNotFoundException, SQLException
    {
        Class.forName(DRIVER_CLASS);
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }
}

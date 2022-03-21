package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection
{
    public static final String DRIVER_CLASS         = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String CONNECTION_STRING    = "jdbc:sqlserver://10.176.111.31;database=CSe21A_16_BankAccount";

    public static final String USERNAME = "CSe21A_16";
    public static final String PASSWORD = "CSe21A_16";


    public static Connection get() throws ClassNotFoundException, SQLException
    {
        Class.forName(DRIVER_CLASS);
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }
}

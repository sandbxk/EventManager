package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


public class DBConnection
{
    private SQLServerDataSource dataSource;
    private Properties property;

    public DBConnection() throws IOException {
        property = new Properties();
        dataSource = new SQLServerDataSource();

        try {
            property.load(new FileReader("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource.setServerName(property.getProperty("server"));
        dataSource.setDatabaseName(property.getProperty("database"));
        dataSource.setUser(property.getProperty("usernames"));
        dataSource.setPassword(property.getProperty("password"));
        dataSource.setPortNumber(Integer.parseInt(property.getProperty("port")));
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws IOException, SQLServerException {
        DBConnection dbConnect = new DBConnection();
        dbConnect.getConnection();
    }

}

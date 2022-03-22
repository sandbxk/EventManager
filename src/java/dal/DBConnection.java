package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection
{
    private SQLServerDataSource dataSource;
    private Properties property;

    public DBConnection() throws IOException {
        property = new Properties();
        dataSource = new SQLServerDataSource();
    }

    public Connection getConnection() throws IOException, SQLServerException {
        property.load(new FileReader(".config.properties"));
        dataSource.setServerName(property.getProperty("server"));
        dataSource.setDatabaseName(property.getProperty("database"));
        dataSource.setUser(property.getProperty("username"));
        dataSource.setPassword(property.getProperty("password"));
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws IOException, SQLServerException {
        DBConnection dbConnect = new DBConnection();
        dbConnect.getConnection();
    }

}

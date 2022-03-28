package dal;

import be.EUserType;
import be.UserInfo;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;

public class CoordinatorDAO implements IUserCrudDAO<UserInfo> {

    private DBConnection DBconnect;

    public CoordinatorDAO() {
        try {
            DBconnect = new DBConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute(String sql)
    {
        try
        {
            Statement statement = DBconnect.getConnection().createStatement();
            statement.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(UserInfo input, String username, String password) {

        String sqlUser = "INSERT INTO userTable (userName, loginName, loginPass, userAuth) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psUser = connection.prepareStatement(sqlUser);

            psUser.setString(1, input.getName());
            psUser.setString(2, username);
            psUser.setString(3, password);
            psUser.setInt(4, 2);

            return psUser.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public UserInfo read(int id) throws SQLException, IOException {
        String sqlUser = "SELECT userName FROM UserTable WHERE userID = ?";

        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psSQL = connection.prepareStatement(sqlUser);
            psSQL.setInt(1, id);
            ResultSet rsUser = psSQL.executeQuery();

            String name = rsUser.getString("userName");

            return new UserInfo(id, name, EUserType.EVENT_COORDINATOR);
        }
    }

    @Override
    public boolean update(UserInfo input) {
        try (Connection connection = DBconnect.getConnection()) {
            String SQLUpdate = "UPDATE UserTable SET userName=?, zipCode=?, userAuth=?";
            PreparedStatement psUpdate = connection.prepareStatement(SQLUpdate);
            psUpdate.setString(1, input.getName());
            psUpdate.setInt(2, input.getZipCode());
            psUpdate.setInt(3, input.getType().ordinal());

            if (psUpdate.executeUpdate() != 1) {
                return true;
            }
            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean delete(UserInfo input) {
        try (Connection connection = DBconnect.getConnection()) {
            String SQLDelete = "DELETE FROM UserTable WHERE userID=?";
            PreparedStatement psDelete = connection.prepareStatement(SQLDelete);
            return psDelete.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void createUser(String userName, String login, String password, String email)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
            INSERT INTO UserTable (userName, loginName, loginPass, userAuth ,email)
            VALUES ('%s', '%s', '%s', 1, '%s')
            """.formatted(userName, login, password, email);

            this.execute(sql);
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createVenue (String location, String street, String zipcode)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
                    INSERT INTO Venue (locationName, Streetname, venueZipCode)
                    VALUES ('%s', '%s', '%s')
                    """.formatted(location, street, zipcode);

            this.execute(sql);

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVenue (String location, String street, String zipcode, int venueID)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
                    UPDATE Venue
                    SET locatioName = '%s', '%s', '%s' 
                    WHERE VenueID = '%s';
                    """.formatted(location, street, zipcode, venueID);

            this.execute(sql);

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVenue (int venueID)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
                    DELETE FROM Venue
                    WHERE VenueID = '%s';
                    """.formatted(venueID);

            this.execute(sql);

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

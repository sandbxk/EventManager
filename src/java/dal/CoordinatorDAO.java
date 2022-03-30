package dal;

import be.EUserType;
import be.Event;
import be.UserInfo;
import be.Venue;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private ResultSet query(String sql)
    {
        try {
            Statement statement = DBconnect.getConnection().createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e) {
            return null;
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
                    INSERT INTO Venue (locationName, StreetName, venueZipCode)
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

    public ObservableList<Venue> getAllVenues()
    {
        ObservableList<Venue> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Venue";

        try (Connection connection = DBconnect.getConnection())
        {
            Statement statement = DBconnect.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                int id = result.getInt("VenueID");
                String location = result.getString("locationName");
                String streetName = result.getString("StreetName");
                String zipCode = Integer.toString(result.getInt("venueZipCode"));
                String city = "Place Holder";

                returnList.add(new Venue(id, location, streetName, zipCode, city));
            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Event> getAllEvents()
    {
        ObservableList<Venue> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Events";

        try (Connection connection = DBconnect.getConnection())
        {
            Statement statement = DBconnect.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {

            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createEvent(Event event)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
                    INSERT INTO Events (eventTitle, VenueID, DESCRIPTION, maxSeats, beginAt, endAt, price)
                    VALUES ('%s', '%s', '%s')
                    
                    """.formatted(event.getEventName(), event.getLocation(), event.getDescription(), event.getLocation(), event.getStartDateTime(), event.getEndDateTime(), event.getPriceGroupsProperty()); //Needs to store name

            this.execute(sql);

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateEvent(Event event)
    {

    }

    public void deleteEvent(Event event)
    {
        String sql = """
                    DELETE FROM Events WHERE EventID = '%s'                    
                    """.formatted(event.getId());

        this.execute(sql);
    }

    public void addUserToEvent(UserInfo user, Event event)
    {
        String sql = """
                INSERT INTO userEvent (UserID_FK, EventID_FK)
                VALUES ('%s', '%s')
                """.formatted(user.getId(), event.getId());

        execute(sql);
    }

    public void removeUserFromEvent(UserInfo user, Event event)
    {
        String sql = """
                DELETE FROM userEvent
                WHERE UserID_FK = '%s' AND EventID_FK = '%s'
                """.formatted(user.getId(), event.getId());

        execute(sql);
    }

    public ObservableList<UserInfo> getUsersForEvent(Event event)
    {
        ObservableList<UserInfo> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Users";

        try (Connection connection = DBconnect.getConnection())
        {
            Statement statement = DBconnect.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {

            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}

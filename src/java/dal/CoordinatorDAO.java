package dal;

import be.*;
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

    public void createVenue (String location, String street, int zipcode)
    {
        try (Connection connection = DBconnect.getConnection())
        {
            String sql = """
                    INSERT INTO venue (venueName, StreetName, venueZipCode)
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
                    SET venueName = '%s', streetName = '%s', venueZipCode = '%s' 
                    WHERE id = '%s';
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

        String sql = """
                   DELETE FROM Venue
                   WHERE id = '%s';
                   """.formatted(venueID);

        this.execute(sql);
    }

    public ObservableList<Venue> getAllVenues() throws SQLException
    {
        ObservableList<Venue> returnList = FXCollections.observableArrayList();

        String sql = """
                       SELECT * FROM Venue
                       JOIN cityName ON venue.venueZipCode=cityName.zipCode
                       """;

        Statement statement = DBconnect.getConnection().createStatement();
        ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {
                int id = result.getInt("id");
                String location = result.getString("venueName");
                String streetName = result.getString("streetName");
                String zipCode = Integer.toString(result.getInt("venueZipCode"));
                String city = result.getString("cityName");

                returnList.add(new Venue(id, location, streetName, zipCode, city));
            }

        return returnList;
    }

    public ObservableList<Event> getAllEvents() throws SQLException
    {
        ObservableList<Venue> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Events";

            Statement statement = DBconnect.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {

            }

        return null;
    }

    public void createEvent(Event event) throws SQLException
    {
            String sql = """
                    INSERT INTO Events (eventTitle, venueID, description, maxSeats, beginAt, endAt, price)
                    VALUES ('%s', '%s', '%s')
                    
                    """.formatted(event.getEventName(), event.getLocation(), event.getDescription(), event.getLocation(), event.getStartDateTime(), event.getEndDateTime(), event.getPriceGroupsProperty()); //Needs to store name

            this.execute(sql);
    }

    public void updateEvent(Event event)
    {

    }

    public void deleteEvent(Event event)
    {
        String sql = """
                    DELETE FROM events WHERE id = '%s'                    
                    """.formatted(event.getId());

        this.execute(sql);
    }

    public void addUserToEvent(UserInfo user, Event event)
    {
        String sql = """
                INSERT INTO userEvent (userID, eventID)
                VALUES ('%s', '%s')
                """.formatted(user.getId(), event.getId());

        this.execute(sql);
    }

    public void removeUserFromEvent(UserInfo user, Event event)
    {
        String sql = """
                DELETE FROM userEvent
                WHERE userID = '%s' AND eventID = '%s'
                """.formatted(user.getId(), event.getId());

        this.execute(sql);
    }

    public ObservableList<UserInfo> getUsersForEvent(Event event) throws SQLException
    {
        ObservableList<UserInfo> returnList = FXCollections.observableArrayList();

        String sql = """
                    SELECT * FROM userEvent WHERE EventID_FK = '%s'
                    """.formatted(event.getId());

            Statement statement = DBconnect.getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {

            }

        return null;
    }

    public void createPrice(PriceGroup price)
    {
        String sql = """
                    INSERT INTO priceGroups (name, price, currency)
                    VALUES ('%s', '%s', '%s')
                    
                    """.formatted(price.getName(), price.getPrice(), price.getCurrency());

        this.execute(sql);
    }

    public void deletePrice(PriceGroup price)
    {
        String sql = """
                DELETE FROM priceGroups
                WHERE id = '%s'
                """.formatted(price.getID());

        execute(sql);
    }

    public void updatePrice (PriceGroup price)
    {
         String sql = """
                 UPDATE priceGroups
                 SET name = '%s', price = '%s', currency = '%s'
                 WHERE id = '%s'
                 """.formatted(price.getName(), price.getPrice(), price.getCurrency(), price.getID());

         this.execute(sql);
    }

    public ObservableList<PriceGroup> getPriceGroup()
    {
        try {
            ObservableList<PriceGroup> returnList = FXCollections.observableArrayList();

            String sql = """
                       SELECT * FROM priceGroups
                       """;

        Statement statement = DBconnect.getConnection().createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next())
        {
            int id = result.getInt("id");
            String name = result.getString("name");
            int price = result.getInt("price");
            String currency = result.getString("currency");

            returnList.add(new PriceGroup(id, name, price, currency));
        }
            return returnList;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

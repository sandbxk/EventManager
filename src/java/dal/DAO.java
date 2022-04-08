package dal;

import be.*;
import be.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAO implements IUserCrudDAO<UserInfo> {

    private DBConnection DBconnect;

    public DAO() {
        try {
            DBconnect = new DBConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(UserInfo input, String username, String password) {

        String sqlUser = """
        INSERT INTO userTable (userName, loginName, loginPass, userAuth)
        VALUES (?, ?, ?, ?)
        """;

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
    public UserInfo read(int id) throws SQLException {

        String sqlUser = """
                SELECT userName
                FROM UserTable
                WHERE userID = ?
                """;

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

        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UserInfo input) {
        try (Connection connection = DBconnect.getConnection()) {
            String SQLDelete = "DELETE FROM UserTable WHERE id=?";
            PreparedStatement psDelete = connection.prepareStatement(SQLDelete);
            return psDelete.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void createUser(String userName, String login, String password, String email)
    {
            String sql = """
            INSERT INTO UserTable (userName, loginName, loginPass, userAuth ,email)
            VALUES (?, ?, ?, ?, ?)
            """;

            try (Connection connection = DBconnect.getConnection())
            {
                PreparedStatement psState = connection.prepareStatement(sql);

                psState.setString(1, userName);
                psState.setString(2, login);
                psState.setString(3, password);
                psState.setString(4, email);

                psState.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * VENUES
     * Getters, setters, deleters and readers for venues.
     */

    public void createVenue (String location, String street, int zipCode)
    {
            String sql = """
                    INSERT INTO venue (venueName, StreetName, venueZipCode)
                    VALUES (?, ?, ?)
                    """;

            try (Connection connection = DBconnect.getConnection())
            {
                PreparedStatement psState = connection.prepareStatement(sql);
                psState.setString(1,location);
                psState.setString(2, street);
                psState.setInt(3, zipCode);

                psState.execute();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
    }

    public Venue getVenue (int venueID)
    {
        String sql = """
               SELECT * FROM venue
               JOIN cityName ON venue.venueZipCode=cityName.zipCode
               WHERE id = ?
               """;

        try (Connection connection = DBconnect.getConnection())
             {
                 PreparedStatement psSQL = connection.prepareStatement(sql);
                 psSQL.setInt(1, venueID);

                 ResultSet rsVenue = psSQL.executeQuery();

                    rsVenue.next();

                     int venueIDgotten = rsVenue.getInt("id");
                     String location = rsVenue.getString("venueName");
                     String streetName = rsVenue.getString("streetName");
                     String zipCode = Integer.toString(rsVenue.getInt("venueZipCode"));
                     String city = rsVenue.getString("cityName");

                     return new Venue(venueIDgotten, location, streetName, zipCode, city);

             } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public void updateVenue (String location, String street, String zipcode, int venueID)
    {
            String sql = """
                    UPDATE Venue
                    SET venueName = ?, streetName = ?, venueZipCode = ?
                    WHERE id = ?
                    """;

            try (Connection connection = DBconnect.getConnection())
            {
                PreparedStatement psState = connection.prepareStatement(sql);
                psState.setString(1, location);
                psState.setString(2, street);
                psState.setInt(3, Integer.parseInt(zipcode));
                psState.setInt(4, venueID);

                psState.execute();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
    }

    public void deleteVenue (int venueID)
    {
        String sql = """
                   DELETE FROM Venue
                   WHERE id = ?
                   """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, venueID);
            psState.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<Venue> getAllVenues()
    {
        ObservableList<Venue> returnList = FXCollections.observableArrayList();

        String sql = """
                       SELECT * FROM Venue
                       JOIN cityName ON venue.venueZipCode=cityName.zipCode
                       """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            ResultSet result = psState.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String location = result.getString("venueName");
                String streetName = result.getString("streetName");
                String zipCode = Integer.toString(result.getInt("venueZipCode"));
                String city = result.getString("cityName");

                returnList.add(new Venue(id, location, streetName, zipCode, city));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * EVENTS
     * Getters, setters, readers and deleters for events.
     */

    public int createEvent(Event event, String colour)
    {
        String sql = """
                     INSERT INTO events (eventTitle, venueID, description, maxSeats, ticketsSold, beginAt, endAt, colour, eventImage)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                     """;
        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psSQL = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            psSQL.setString(1, event.getEventName());
            psSQL.setInt(2, event.getLocation().getID());
            psSQL.setString(3, event.getDescription());
            psSQL.setInt(4, event.getTicketsTotal());
            psSQL.setInt(5, event.getTicketsSold());
            psSQL.setTimestamp(6, Timestamp.valueOf(event.getStartDateTime()));
            if (event.getEndDateTime() != null) {
                psSQL.setTimestamp(7, Timestamp.valueOf(event.getEndDateTime()));
            }
            else {
                psSQL.setNull(7, Types.TIMESTAMP);
            }
            psSQL.setString(8, colour);

            if (event.hasImage() == true)
            {
                FileInputStream fileInputStream = new FileInputStream(event.getEventImage().getUrl());
                psSQL.setBinaryStream(9, fileInputStream, fileInputStream.available());
            } else
            {
                psSQL.setNull(9, Types.BINARY);
            }

            psSQL.execute();

            try (ResultSet keys = psSQL.getGeneratedKeys()) {
            keys.next();
            return keys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateEvent(Event event, String colour)
    {
        String sql = """
                UPDATE events
                SET eventTitle = ?, venueID = ?, description = ?, maxSeats = ?, ticketsSold = ?, beginAt = ?, endAt = ?, colour = ?, eventImage =?
                WHERE id = ?
                """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);

            psState.setString(1, event.getEventName());
            psState.setInt(2, event.getLocation().getID());
            psState.setString(3, event.getDescription());
            psState.setInt(4, event.getTicketsTotal());
            psState.setInt(5, event.getTicketsSold());
            psState.setTimestamp(6, Timestamp.valueOf(event.getStartDateTime()));
            psState.setTimestamp(7, Timestamp.valueOf(event.getEndDateTime()));
            psState.setString(8, colour);

            if (event.hasImage())
            {
                FileInputStream fileInputStream = new FileInputStream(event.getEventImage().getUrl());
                psState.setBinaryStream(9, fileInputStream, fileInputStream.available());
            } else
            {
                psState.setNull(9, Types.BINARY);
            }

            psState.setInt(10, event.getId());

            psState.execute();

        } catch (SQLException | FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteEvent(int eventID)
    {
        String sql = """
                    DELETE FROM events 
                    WHERE id = ?
                    """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, eventID);

            psState.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<Event> getAllEvents()
    {
        ObservableList<Event> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM events";

        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psSQL = connection.prepareStatement(sql);

            ResultSet rsEvent = psSQL.executeQuery();

            while (rsEvent.next())
            {
                int id = rsEvent.getInt("id");
                String title = rsEvent.getString("eventTitle");
                int venueID = rsEvent.getInt("venueID");
                String description = rsEvent.getString("description");
                int maxSeats = rsEvent.getInt("maxSeats");
                int ticketsSold = rsEvent.getInt("ticketsSold");
                LocalDateTime startTime = rsEvent.getTimestamp("beginAt").toLocalDateTime();
                LocalDateTime endTime = rsEvent.getTimestamp("endAt").toLocalDateTime();

                String colour = rsEvent.getString("colour");
                String[] rgb = colour.split(",");
                Color rbgColor = Color.color(Double.parseDouble(rgb[0]), Double.parseDouble(rgb[1]), Double.parseDouble(rgb[2]));

                Venue venue = getVenue(venueID);

                ObservableList<PriceGroup> priceGroups = getPriceGroup(id);

                Image eventImage = null;

                InputStream is = rsEvent.getBinaryStream("eventImage");
                if (is != null) {
                    eventImage = new Image(is, 350, 400, true, true);
                }

                ObservableList<UserInfo> attendeesList = getUsersForEvent(id);

                returnList.add(new Event(id, title, startTime, endTime, venue, ticketsSold, maxSeats, priceGroups, description, rbgColor, eventImage, attendeesList));
            }
            return returnList;
        } catch (SQLException e) {
            e.printStackTrace();
            return returnList;
        }
    }

    /**
     * PRICES
     * Getters, setters, readers and deleters for price management.
     */

    public void createPrice(String name, int price, String currency, int eventID)
    {
        String sql = """
                    INSERT INTO priceEvent (name, price, currency, eventID)
                    VALUES (?, ?, ?, ?)
                    """;
        try (Connection conneciton = DBconnect.getConnection())
        {
            PreparedStatement psPrice = conneciton.prepareStatement(sql);

            psPrice.setString(1, name);
            psPrice.setInt(2, price);
            psPrice.setString(3, currency);
            psPrice.setInt(4, eventID);

            psPrice.execute();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deletePrice(int priceID)
    {
        String sql = """
                DELETE FROM priceEvent
                WHERE id = ?
                """;
        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, priceID);

            psState.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updatePrice (String name, int price, String currency, int priceID)
    {
         String sql = """
                 UPDATE priceEvent
                 SET name = ?, price = ?, currency = ?
                 WHERE id = ?
                 """;

         try (Connection connection = DBconnect.getConnection())
         {
             PreparedStatement psState = connection.prepareStatement(sql);
             psState.setString(1, name);
             psState.setInt(2, price);
             psState.setString(3, currency);
             psState.setInt(4, priceID);

             psState.execute();

         } catch (SQLException e)
         {
             e.printStackTrace();
         }
    }

    public ObservableList<PriceGroup> getPriceGroup(int eventID)
    {
        ObservableList<PriceGroup> returnList = FXCollections.observableArrayList();

        String sql = """
                       SELECT * FROM priceEvent
                       WHERE eventID = ?
                       """;

            try (Connection connection = DBconnect.getConnection()){

            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, eventID);

            ResultSet result = psState.executeQuery();

        while (result.next())
        {
            int priceid = result.getInt("id");
            String name = result.getString("name");
            int price = result.getInt("price");
            String currency = result.getString("currency");

            returnList.add(new PriceGroup(priceid, name, price, currency));
        }
            return returnList;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void createPriceGroup(ObservableList<PriceGroup> priceGroups, int eventID) {
        String sql = """
                INSERT INTO priceEvent (name, price, currency, eventID)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psState = connection.prepareStatement(sql);
            for (PriceGroup price : priceGroups) {
                psState.setString(1, price.getName());
                psState.setInt(2, price.getPrice());
                psState.setString(3, price.getCurrency());
                psState.setInt(4, eventID);

                psState.addBatch();
            }

            psState.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllPrices(int eventID)
    {
        String sql = """
                DELETE FROM priceEvent
                WHERE eventID = ?        
                """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, eventID);

            psState.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * USERS
     * Deleters, readers, setters and getters for users.
     */

    public ObservableList<UserInfo> getAllUsers()
    {
        ObservableList<UserInfo> returnList = FXCollections.observableArrayList();

        String sql = """
                    SELECT * FROM userTable
                     """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            ResultSet resSet = psState.executeQuery();

            while (resSet.next())
            {
                EUserType userType;

                int userID = resSet.getInt("id");
                String name = resSet.getString("userName");
                if (resSet.getInt("userAuth") == 1) {
                    userType = EUserType.EVENT_COORDINATOR;
                } else {
                    userType = EUserType.END_USER;
                }
                int zipCode = resSet.getInt("ZipCode");
                String email = resSet.getString("email");


                returnList.add(new UserInfo(userID, name, userType, zipCode, email));
            }
        return returnList;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void addUserToEvent(List<UserInfo> userList, int eventID)
    {
        String sql = """
                INSERT INTO userEvent (userID, eventID)
                VALUES (?, ?)
                """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);

            for (UserInfo user: userList) {
                psState.setInt(1, user.getId());
                psState.setInt(2, eventID);

                psState.addBatch();
            }

            psState.executeBatch();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public void removeUserFromEvent(int userID, int eventID)
    {
        String sql = """
                DELETE FROM userEvent
                WHERE userID = ? AND eventID = ?
                """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, userID);
            psState.setInt(2, eventID);

            psState.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<UserInfo> getUsersForEvent(int eventID)
    {
        ObservableList<UserInfo> returnList = FXCollections.observableArrayList();

        String sql = """
                    SELECT * FROM userEvent
                    JOIN userTable ON userEvent.userID=userTable.id
                    WHERE eventID = ?
                    """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, eventID);

            ResultSet resSet = psState.executeQuery();

            while (resSet.next())
            {
                EUserType userType;

                int userID = resSet.getInt("id");
                String name = resSet.getString("userName");
                if (resSet.getInt("userAuth") == 1 )
                {
                    userType = EUserType.EVENT_COORDINATOR;
                } else
                {
                    userType = EUserType.END_USER;
                }
                int zipCode = resSet.getInt("ZipCode");
                String email = resSet.getString("email");


                returnList.add(new UserInfo(userID, name, userType, zipCode, email));
            }

            return returnList;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAllUsersFromEvent (int eventID)
    {
        String sql = """
                DELETE FROM userEvent
                WHERE eventID = ?
                """;

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psState = connection.prepareStatement(sql);
            psState.setInt(1, eventID);

            psState.execute();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<UserInfo> getAttendeesFromEvent(Event event) {
        List<UserInfo> users = new ArrayList<>();
        String sql = ("SELECT id, userName, email FROM userTable WHERE id = (SELECT userID FROM userEvent WHERE eventID = ?)");
        try (Connection connection = DBconnect.getConnection()) {
            PreparedStatement psSQL = connection.prepareStatement(sql);
            psSQL.setInt(1, event.getId());

            ResultSet rsUser = psSQL.executeQuery();

            while (rsUser.next())
            {
                int id = rsUser.getInt("id");
                String name = rsUser.getString("userName");
                String email = rsUser.getString("email");
                UserInfo user = new UserInfo(id,name, EUserType.END_USER);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
}

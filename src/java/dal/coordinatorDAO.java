package dal;

import be.EUserType;
import be.UserInfo;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class coordinatorDAO implements IUserCrudDAO<UserInfo> {

    private DBConnection DBconnect;

    public coordinatorDAO() throws IOException {
        DBconnect = new DBConnection();
    }

    @Override
    public boolean create(UserInfo input,String username,String password) {

        String sqlUser = "INSERT INTO userTable (userName, loginName, loginPass, zipCode, userAuth) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psUser = connection.prepareStatement(sqlUser);

            psUser.setString(1, input.name());
            psUser.setString(2, username);
            psUser.setString(3, password);
            psUser.setInt(4, input.zipCode());
            psUser.setInt(5, input.type().ordinal());

            return psUser.execute();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public UserInfo read(int id) throws SQLException, IOException {
        String sqlUser = "SELECT userName FROM UserTable WHERE userID = ?";

        try (Connection connection = DBconnect.getConnection())
        {
            PreparedStatement psSQL = connection.prepareStatement(sqlUser);
            psSQL.setInt(1,id);
            ResultSet rsUser = psSQL.executeQuery();

            String name = rsUser.getString("userName");

            return new UserInfo(id,name, EUserType.EVENT_COORDINATOR);
        }
    }

    @Override
    public boolean update(UserInfo input) {
        try (Connection connection = DBconnect.getConnection())
        {
            String SQLUpdate = "UPDATE UserTable SET userName=?, zipCode=?, userAuth=?";
            PreparedStatement psUpdate = connection.prepareStatement(SQLUpdate);
            psUpdate.setString(1,input.name());
            psUpdate.setInt(2,input.zipCode());
            psUpdate.setInt(3,input.type().ordinal());

            if (psUpdate.executeUpdate() != 1)
            {
                return true;
            }
            else return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(UserInfo input) {
        return false;
    }
}

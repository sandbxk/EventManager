package dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.SQLException;

public interface IUserCrudDAO<CLS> {

    boolean create(CLS input, String username, String password);

    CLS read(int id) throws SQLException, IOException;

    boolean update(CLS input);

    boolean delete(CLS input);


}

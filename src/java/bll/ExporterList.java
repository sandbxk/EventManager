package bll;

import be.Event;
import be.UserInfo;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.CoordinatorDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExporterList {


public void createListOfAttendees(Event event) throws SQLServerException, IOException {
    CoordinatorDAO CDAO = new CoordinatorDAO();
    FileWriter fl = new FileWriter(new File(".design/lister/"+ event.getEventName() +".txt"));
    List<UserInfo> users = new ArrayList<>();
    CDAO.getAttendeesFromEvent(event);
    for (UserInfo user: users)
    {
        fl.append(user.getName() +" | " + user.getEmail() + "\n");
    }
}
}

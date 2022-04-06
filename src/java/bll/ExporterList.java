package bll;

import be.Event;
import be.UserInfo;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExporterList {


public void createListOfAttendees(Event event) throws SQLServerException, IOException {
    DAO CDAO = new DAO();
    FileWriter fl = new FileWriter(new File(".design/lister/"+ event.getEventName() +".txt"));
    List<UserInfo> users = new ArrayList<>();
    CDAO.getAttendeesFromEvent(event);
    for (UserInfo user: users)
    {
        fl.append(user.getName() +" | " + user.getEmail() + "\n");
    }
}
}

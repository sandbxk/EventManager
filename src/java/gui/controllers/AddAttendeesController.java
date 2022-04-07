package gui.controllers;

import be.UserInfo;
import bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;


public class AddAttendeesController implements Initializable {

    @FXML public TableView<UserInfo> tblAllUsersTable;
    @FXML public TableColumn<UserInfo, String> tblClmAttName;

    @FXML public TableView<UserInfo> tblAddedUsers;

    private AddAttendeesController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initTableViews();
    }

    private void initTableViews()
    {
        tblAddedUsers.setItems(DataManager.getInstance().getAllUsers());
    }

    public void onSaveNewAttendee(ActionEvent actionEvent)
    {
    }

    public void onCancelAddAttendee(ActionEvent actionEvent)
    {
    }
}

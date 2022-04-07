package gui.controllers;

import be.Event;
import be.PriceGroup;
import be.UserInfo;
import bll.DataManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class AddAttendeesController implements Initializable {

    @FXML public TableView<UserInfo> tblAllUsersTable;
    @FXML public TableColumn<UserInfo, String> tblClmAttName;

    @FXML public TableView<UserInfo> tblAddedUsers;
    @FXML public TableColumn<UserInfo, String> clmAddedUserName;

    @FXML public Button btnAddUser;
    @FXML public Button btnRemoveUser;
    @FXML public Button btnAttendeeSave;
    @FXML public Button btnAttendeeCancel;

    @FXML public ComboBox<PriceGroup> comboBoxTicketType;

    public AddAttendeesController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initTableViews();
        initEventListeners();
    }

    public void initTableViews()
    {
        tblAllUsersTable.setItems(DataManager.getInstance().getAllUsers());
    }

    public void initEventListeners()
    {
        tblClmAttName.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("nameProperty"));

        clmAddedUserName.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("nameProperty"));
    }

    public void onSaveNewAttendee(ActionEvent actionEvent)
    {
    }

    public void onCancelAddAttendee(ActionEvent actionEvent)
    {
    }

    public void addAttendeeToList(ActionEvent actionEvent)
    {
        UserInfo selection = tblAllUsersTable.getSelectionModel().getSelectedItem();

        if (selection != null) {
            tblAddedUsers.getItems().add(new UserInfo(selection.getId(), selection.getName(), selection.getType()));
        }
    }

    public void removeAttendeeFromList(ActionEvent actionEvent)
    {
        UserInfo selection = tblAddedUsers.getSelectionModel().getSelectedItem();

        if(selection != null)
        {
            tblAddedUsers.getItems().remove(UserInfo);
        }
    }
}

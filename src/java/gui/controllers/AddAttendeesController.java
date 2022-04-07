package gui.controllers;

import be.PriceGroup;
import be.UserInfo;
import bll.DataManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.List;
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
        tblClmAttName.setCellValueFactory(new PropertyValueFactory<>("nameProperty"));
        clmAddedUserName.setCellValueFactory(new PropertyValueFactory<>("nameProperty"));
    }

    /**
     * Saves the list of people to add to an event and closes the menu, or closes the menu without saving.
     */

    public void onSaveNewAttendee(ActionEvent actionEvent)
    {
        //TODO Save list and add users to database for the selected event.
        DataManager.getInstance().addUserToEvent(tblAddedUsers, DataManager.getInstance().getSelectedEvent());

        Stage stage = (Stage) btnAttendeeCancel.getScene().getWindow();
        stage.close();
    }

    public void onCancelAddAttendee(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnAttendeeCancel.getScene().getWindow();
        stage.close();
     }

    /**
     * Adds/Removes Users from the tableViews.
     */

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
            tblAddedUsers.getItems().remove(selection);
        }
    }

    /**
     * Methods
     */

}

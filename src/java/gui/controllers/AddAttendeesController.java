package gui.controllers;

import be.PriceGroup;
import be.UserInfo;
import bll.DataManager;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddAttendeesController implements Initializable {

    @FXML public TextField txtUserSearch;

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
    }

    public void initEventListeners()
    {
        tblClmAttName.setCellValueFactory(param -> param.getValue().getNameProperty());
        clmAddedUserName.setCellValueFactory(param -> param.getValue().getNameProperty());

        //Wrap ObservableList of UserInfo in a FilteredList.
        FilteredList<UserInfo> filteredData = new FilteredList<>(DataManager.getInstance().getAllUsers(), b -> true);

        //Sets the filter predict when filter changes.
        txtUserSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {

                //If filter is empty, display all users.
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }

                //Compare user name with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getName().toLowerCase().indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                } else return false;

            });
        });

        SortedList<UserInfo> sortedUsers = new SortedList<>(filteredData);

        sortedUsers.comparatorProperty().bind(tblAllUsersTable.comparatorProperty());

        tblAllUsersTable.setItems(sortedUsers);
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

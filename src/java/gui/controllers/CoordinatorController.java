package gui.controllers;

import be.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {


    @FXML public Button btnCreateActions;
    @FXML public Label lblUser;
    @FXML public ImageView imgViewUser;

    @FXML public FlowPane flowPaneEvents;

    @FXML public ImageView imgViewEvent;
    @FXML public Button btnEditEvent;
    @FXML public Label lblEventName;
    @FXML public Label lblEventDate;
    @FXML public Label lblEventTime;
    @FXML public Label lblEventVenue;
    @FXML public Label lblRemainingTickets;
    @FXML public Label lblSoldTickets;
    @FXML public TableView tblViewTicketGroup;
    @FXML public TableColumn tblClmnGroupName;
    @FXML public TableColumn tblClmnGroupPrice;
    @FXML public TextArea txtAreaInfo;

    @FXML public TextField txtFieldSearch;
    @FXML public Button btnSearch;
    @FXML public TableView tblViewAttendees;
    @FXML public TableColumn tblClmAttFirstName;
    @FXML public TableColumn tblClmAttLastName;
    @FXML public TableColumn tblClmAttEmail;
    @FXML public TableColumn tblClmAttPhone;
    @FXML public TableColumn tblClmAttTicketGroup;
    @FXML public TableColumn tblClmAttTicketNo;
    @FXML public TableColumn tblClmAttSeatNo;

    @FXML public FlowPane flowPaneAttendeeBtns;

    ToggleGroup eventToggle;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        eventToggle = new ToggleGroup();

    }

    public void onCreate(ActionEvent event)
    {
        Event event1 = new Event();
        flowPaneEvents.getChildren().add(event1.getEventTile());
        eventToggle.getToggles().add(event1.getEventTile());
    }

}

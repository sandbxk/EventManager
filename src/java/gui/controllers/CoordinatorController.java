package gui.controllers;

import be.Event;
import bll.DataManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
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

    private ObjectProperty<Event> selectedEvent;

    public CoordinatorController() {
        selectedEvent = new SimpleObjectProperty();
        // fixme: //selectedEvent.bind(DataManager.getInstance().getSelectedEventProperty());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        eventToggle = new ToggleGroup();
        initEventListener();

    }

    private void initEventListener() {
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            imgViewEvent.setImage(newValue.getEventImage());
            lblEventName.setText(newValue.getEventName());
            lblEventDate.setText(newValue.getStartDateTime().toLocalDate().toString());
            lblEventTime.setText(newValue.getStartDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
            lblEventVenue.setText(newValue.getLocation().getVenueName());

            txtAreaInfo.setText(newValue.getDescription());

            lblSoldTickets.setText(newValue.getTicketsSold() + "");
            lblRemainingTickets.setText(newValue.getTicketsRemaining() + "");

            //tblview
        });
    }

    public void onCreate(ActionEvent event)
    {
        Parent root = null;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/NewEventView.fxml")));
            stage.setTitle("New Event");
            stage.setMinWidth(511);
            stage.setMinHeight(737);
            stage.setScene(new Scene(root, 511, 737));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Event event1 = new Event();
        flowPaneEvents.getChildren().add(event1.getEventTile());
        eventToggle.getToggles().add(event1.getEventTile());
    }

}

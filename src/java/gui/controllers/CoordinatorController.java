package gui.controllers;

import be.Event;
import be.PriceGroup;
import bll.DataManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML public Button btnDeleteEvent;
    @FXML public Label lblUser;
    @FXML public ImageView imgViewUser;

    @FXML public FlowPane flowPaneEvents;

    @FXML public AnchorPane imgDetailBackground;
    @FXML public ImageView imgViewEvent;
    @FXML public Button btnEditEvent;
    @FXML public Label lblEventName;
    @FXML public Label lblEventDate;
    @FXML public Label lblEventTime;
    @FXML public Label lblEventVenue;
    @FXML public Label lblRemainingTickets;
    @FXML public Label lblSoldTickets;
    @FXML public TableView<PriceGroup> tblViewTicketGroup;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupName;
    @FXML public TableColumn<PriceGroup, Number> tblClmnGroupPrice;
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
        selectedEvent.bind(DataManager.getInstance().getSelectedEventProperty());
    }

    //TODO: Fix event CSS, Fix tablecell text alignment, fix tblviewTicketGroup width, header and height in coordinatorView
    //TODO: Fix detailImageView
    //TODO: tblViewAttendees with
    //TODO: Fix alert CSS error


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        eventToggle = new ToggleGroup();
        initTableViewPriceGroups();
        initEventListener();

    }

    private void initTableViewPriceGroups(){
        tblClmnGroupName.setCellValueFactory(param -> param.getValue().nameProperty());
        tblClmnGroupPrice.setCellValueFactory(param -> param.getValue().priceProperty());
    }

    private void initEventListener() {
        selectedEvent.addListener((observable, oldValue, newValue) -> {
            imgViewEvent.setImage(newValue.getEventImage());
            imgDetailBackground.setStyle("-fx-background-color: rgb(" + newValue.getColor().getRed() +", " + newValue.getColor().getGreen() + ", " + newValue.getColor().getBlue() + ");");
            lblEventName.setText(newValue.getEventName());
            lblEventDate.setText(newValue.getStartDateTime().toLocalDate().toString());
            lblEventTime.setText(newValue.getStartDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            lblEventVenue.setText(newValue.getLocation().getVenueName());

            txtAreaInfo.setText(newValue.getDescription());

            lblSoldTickets.setText(newValue.getTicketsSold() + "");
            lblRemainingTickets.setText(newValue.getTicketsRemaining() + "");

            tblViewTicketGroup.setItems(newValue.getPriceGroups());
            
            //TODO: tblview Attendees
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
            stage.setScene(new Scene(root, 549, 750));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setOnHiding(event1 -> {
            flowPaneEvents.getChildren().clear();
            ObservableList<Event> allEvents = DataManager.getInstance().getAllEvents();
            for (Event e : allEvents){
                flowPaneEvents.getChildren().add(e.getEventTile());
                eventToggle.getToggles().add(e.getEventTile());
            }
        });
    }

    public void onUser(MouseEvent mouseEvent) {
    }

    public void onDeleteEvent(ActionEvent event) {
    }

    public void onEditEvent(ActionEvent event) {
    }
}

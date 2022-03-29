package gui.controllers;

import be.Event;
import be.PriceGroup;
import bll.DataManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
import javafx.util.Duration;

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

    @FXML public ToggleButton tglBtnShowHideMenu;
    @FXML public AnchorPane anchorPaneAttendeeMenu;
    @FXML public Button btnSendTicket;
    @FXML public Button btnDLTicket;
    @FXML public Button btnEditAttendeeInfo;
    @FXML public Button btnRemoveAttendee;

    private ToggleGroup eventToggle;
    private static final double SLIDE_MENU_WIDTH = 500;
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
        hideMenuInit();
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
            imgDetailBackground.setStyle("-fx-background-color: rgb(" + newValue.getColor().getRed()*255 +", " + newValue.getColor().getGreen()*255 + ", " + newValue.getColor().getBlue()*255 + ");");
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
        ObservableList toggles = FXCollections.observableArrayList();
        stage.setOnHiding(event1 -> {
            flowPaneEvents.getChildren().clear();
            ObservableList<Event> allEvents = DataManager.getInstance().getAllEvents();
            for (Event e : allEvents){
                flowPaneEvents.getChildren().add(e.getEventTile());
                toggles.addAll(e.getEventTile());
            }
            eventToggle.getToggles().clear();
            eventToggle.getToggles().addAll(toggles);
        });
    }

    public void onUser(MouseEvent mouseEvent) {
    }

    public void onDeleteEvent(ActionEvent event) {
    }

    public void onEditEvent(ActionEvent event) {
    }

    private void hideMenuInit(){
        btnSendTicket.setOpacity(0);
        btnDLTicket.setOpacity(0);
        btnEditAttendeeInfo.setOpacity(0);
        btnRemoveAttendee.setOpacity(0);
        anchorPaneAttendeeMenu.setOpacity(0);
    }

    public void onShowHideMenu(ActionEvent event) {
        Timeline timeline = new Timeline();
        Timeline opacity = new Timeline();
        if (tglBtnShowHideMenu.isSelected()){
            tglBtnShowHideMenu.setText("a");


            KeyFrame btn1KF = new KeyFrame(Duration.millis(100), new KeyValue(btnSendTicket.opacityProperty(), 1));
            KeyFrame btn2KF = new KeyFrame(Duration.millis(100), new KeyValue(btnDLTicket.opacityProperty(), 1));
            KeyFrame btn3KF = new KeyFrame(Duration.millis(100), new KeyValue(btnEditAttendeeInfo.opacityProperty(), 1));
            KeyFrame btn4KF = new KeyFrame(Duration.millis(100), new KeyValue(btnRemoveAttendee.opacityProperty(), 1));
            opacity.getKeyFrames().addAll(btn1KF, btn2KF, btn3KF, btn4KF);

            KeyFrame paneKF= new KeyFrame(Duration.millis(100), new KeyValue(anchorPaneAttendeeMenu.opacityProperty(), 1));
            Timeline paneOpacity = new Timeline(paneKF);



            KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.maxWidthProperty(), SLIDE_MENU_WIDTH));
            KeyFrame kf2 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.prefWidthProperty(), SLIDE_MENU_WIDTH));
            KeyFrame kf3 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.minWidthProperty(), SLIDE_MENU_WIDTH));

            timeline.getKeyFrames().addAll(kf1, kf2, kf3);

            paneOpacity.play();
            paneOpacity.setOnFinished(event1 -> timeline.play());
            timeline.setOnFinished(event1 -> opacity.play());
        }
        else {
            tglBtnShowHideMenu.setText("˅");

            KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.maxWidthProperty(),0));
            KeyFrame kf2 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.prefWidthProperty(), 0));
            KeyFrame kf3 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.minWidthProperty(), 0));

            KeyFrame btn1KF = new KeyFrame(Duration.millis(100), new KeyValue(btnSendTicket.opacityProperty(), 0));
            KeyFrame btn2KF = new KeyFrame(Duration.millis(100), new KeyValue(btnDLTicket.opacityProperty(), 0));
            KeyFrame btn3KF = new KeyFrame(Duration.millis(100), new KeyValue(btnEditAttendeeInfo.opacityProperty(), 0));
            KeyFrame btn4KF = new KeyFrame(Duration.millis(100), new KeyValue(btnRemoveAttendee.opacityProperty(), 0));

            Timeline btnOpacity = new Timeline(btn1KF, btn2KF, btn3KF, btn4KF);
            timeline.getKeyFrames().addAll(kf1, kf2, kf3);

            btnOpacity.play();

            btnOpacity.setOnFinished(event1 -> timeline.play());
            timeline.setOnFinished(event1 -> {
                KeyFrame paneKF = new KeyFrame(Duration.millis(100), new KeyValue(anchorPaneAttendeeMenu.opacityProperty(), 0));
                opacity.getKeyFrames().add(paneKF);
                opacity.play();
            });
        }
    }

    public void onDownloadTicket(ActionEvent event) {
    }

    public void onSendTicket(ActionEvent event) {
    }

    public void onEditInfo(ActionEvent event) {
    }

    public void onRemoveAttendee(ActionEvent event) {
    }
}

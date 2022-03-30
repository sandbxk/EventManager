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
import javafx.scene.Node;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {


    @FXML public Button btnCreateActions;
    @FXML public Button btnDeleteEvent;
    @FXML public Label lblUser;
    @FXML public ImageView imgViewUser;

    @FXML public FlowPane flowPaneEvents;

    @FXML public AnchorPane imgDetailBackground;
    @FXML public ImageView imgViewEvent;
    @FXML public Button btnEventActions;
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
    private static final double SLIDE_MENU_WIDTH = 490;
    private ObjectProperty<Event> selectedEvent;
    private ContextMenu eventActionsMenu;



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
        onShowHideMenu(new ActionEvent());
        initTableViewPriceGroups();
        initEventListener();
        initEventActionsMenu();

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

    public void onDeleteEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete the event?");
        alert.setContentText("This cannot be undone");
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.out.println("delete");
        }
    }

    public void onEditEvent() {
        System.out.println("edit");
    }

    /**
     * Hides or shows the menu for attendee action (e.g. delete, edit, send ticket) through an animation.
     * Both the size, opacity, and rotation of the showHide button are rotated for a smooth look.
     * @param event
     */
    public void onShowHideMenu(ActionEvent event) {
        Timeline timeline = new Timeline();
        Timeline opacity = new Timeline();
        if (tglBtnShowHideMenu.isSelected()){
            //Rotation of the pressed button
            Timeline btnRotate = new Timeline(new KeyFrame(Duration.millis(100), new KeyValue(tglBtnShowHideMenu.rotateProperty(), -90)));

            //Opacity of the buttons inside the menu
            KeyFrame btn1KF = new KeyFrame(Duration.millis(100), new KeyValue(btnSendTicket.opacityProperty(), 1));
            KeyFrame btn2KF = new KeyFrame(Duration.millis(100), new KeyValue(btnDLTicket.opacityProperty(), 1));
            KeyFrame btn3KF = new KeyFrame(Duration.millis(100), new KeyValue(btnEditAttendeeInfo.opacityProperty(), 1));
            KeyFrame btn4KF = new KeyFrame(Duration.millis(100), new KeyValue(btnRemoveAttendee.opacityProperty(), 1));
            opacity.getKeyFrames().addAll(btn1KF, btn2KF, btn3KF, btn4KF);

            //Opacity for the menu container
            KeyFrame paneKF= new KeyFrame(Duration.millis(100), new KeyValue(anchorPaneAttendeeMenu.opacityProperty(), 1));
            Timeline paneOpacity = new Timeline(paneKF);

            //Size for the menu container
            KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.maxWidthProperty(), SLIDE_MENU_WIDTH));
            KeyFrame kf2 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.prefWidthProperty(), SLIDE_MENU_WIDTH));
            KeyFrame kf3 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.minWidthProperty(), SLIDE_MENU_WIDTH));
            timeline.getKeyFrames().addAll(kf1, kf2, kf3);

            btnRotate.play();
            btnRotate.setOnFinished(event1 -> paneOpacity.play());
            paneOpacity.setOnFinished(event1 -> timeline.play());
            timeline.setOnFinished(event1 -> opacity.play());
        }
        else {
            //Rotation of the pressed button
            Timeline btnRotate = new Timeline(new KeyFrame(Duration.millis(100), new KeyValue(tglBtnShowHideMenu.rotateProperty(), 0)));

            //Size of the menu container
            KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.maxWidthProperty(),0));
            KeyFrame kf2 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.prefWidthProperty(), 0));
            KeyFrame kf3 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.minWidthProperty(), 0));
            timeline.getKeyFrames().addAll(kf1, kf2, kf3);

            //Opacity of the buttons inside the menu
            KeyFrame btn1KF = new KeyFrame(Duration.millis(100), new KeyValue(btnSendTicket.opacityProperty(), 0));
            KeyFrame btn2KF = new KeyFrame(Duration.millis(100), new KeyValue(btnDLTicket.opacityProperty(), 0));
            KeyFrame btn3KF = new KeyFrame(Duration.millis(100), new KeyValue(btnEditAttendeeInfo.opacityProperty(), 0));
            KeyFrame btn4KF = new KeyFrame(Duration.millis(100), new KeyValue(btnRemoveAttendee.opacityProperty(), 0));
            Timeline btnOpacity = new Timeline(btn1KF, btn2KF, btn3KF, btn4KF);


            btnRotate.play();

            btnRotate.setOnFinished(event1 -> btnOpacity.play());
            btnOpacity.setOnFinished(event1 -> timeline.play());
            //Opacity of the menu container
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

    public void onAttendeeEditInfo(ActionEvent event) {
    }

    public void onRemoveAttendee(ActionEvent event) {
    }

    public void onEventActions(ActionEvent event) {
        //Location of the pressed button
        double onScreenX = btnEventActions.getScene().getWindow().getX() + btnEventActions.getHeight() + btnEventActions.localToScene(btnEventActions.getBoundsInLocal()).getMinX();
        double onScreenY = btnEventActions.getScene().getWindow().getY() + btnEventActions.getWidth() + btnEventActions.localToScene(btnEventActions.getBoundsInLocal()).getMinY();

        double offsetX = btnEventActions.getWidth() * 2;
        double offsetY = btnEventActions.getHeight()*1.5;

        //ContextMenu showed at the location of the button, with offsets applied
        eventActionsMenu.show(btnEventActions, onScreenX - offsetX, onScreenY + offsetY);
    }

    private void initEventActionsMenu(){
        MenuItem editEvent = new MenuItem("Edit Event");
        editEvent.setOnAction(event -> onEditEvent());
        MenuItem deleteEvent = new MenuItem("Delete Event");
        deleteEvent.setOnAction(event -> onDeleteEvent());


        eventActionsMenu = new ContextMenu(editEvent, deleteEvent);
        eventActionsMenu.setAutoHide(true);
    }
}

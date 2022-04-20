package gui.controllers;

import be.Event;
import be.PriceGroup;
import be.UserInfo;
import bll.DataManager;
import bll.ExporterList;
import bll.Search;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {


    @FXML public Button btnCreateActions;
    @FXML public Label lblUser;
    @FXML public ImageView imgViewUser;

    @FXML public FlowPane flowPaneEvents;


    @FXML public AnchorPane imgDetailBackground;
    @FXML public ImageView imgViewEvent;
    @FXML public Button btnEventActions;
    @FXML public Label lblEventStartDateLabel;
    @FXML public Label lblEventDateEndLabel;
    @FXML public Label lblEventName;
    @FXML public Label lblEventDateStart;
    @FXML public Label lblEventDateEnd;
    @FXML public Label lblEventTimeStart;
    @FXML public Label lblEventTimeEnd;
    @FXML public Label lblEventVenue;
    @FXML public Label lblTotalTickets;
    @FXML public Label lblSoldTickets;
    @FXML public Label lblRemainingTickets;
    @FXML public TableView<PriceGroup> tblViewTicketGroup;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupName;
    @FXML public TableColumn<PriceGroup, Number> tblClmnGroupPrice;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupCurrency;
    @FXML public TextArea txtAreaInfo;

    @Deprecated
    @FXML public ImageView imgViewTicketIcon;
    @FXML public Label lblEventDateSpacer1;
    @FXML public Label lblEventDateSpacer2;
    @FXML public Label lblEventDateSpacer3;
    @FXML public Label lblEventDateSpacer4;
    @FXML public Label lblAtVenue;
    @FXML public Label lblDescriptionHeader;
    @FXML public Label lblRemaining;
    @FXML public Label lblSold;
    @FXML public Label lblTotal;
    @FXML public Label lblTicketPricing;

    @FXML public TextField txtFieldSearch;
    @FXML public Button btnSearch;
    @FXML public TableView<UserInfo> tblViewAttendees;
    @FXML public TableColumn<UserInfo, String> tblClmAttFirstName;
    @FXML public TableColumn<UserInfo, String> tblClmAttLastName;
    @FXML public TableColumn<UserInfo, String> tblClmAttEmail;
    @FXML public TableColumn tblClmAttPhone;
    @FXML public TableColumn tblClmAttTicketGroup;
    @FXML public TableColumn tblClmAttTicketNo;

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
    private ContextMenu signOutMenu;
    private BooleanProperty showingAddress;
    private ExporterList expoList;
    private Search searcher;


    public CoordinatorController() {
        selectedEvent = new SimpleObjectProperty();
        selectedEvent.bind(DataManager.getInstance().getSelectedEventProperty());
        showingAddress = new SimpleBooleanProperty();
        showingAddress.set(false);
        expoList = new ExporterList();
        searcher = new Search();


    }

    //TODO: Coordinator TableView init


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventToggle = new ToggleGroup();
        onShowHideMenu(new ActionEvent());
        hideDetailsPanelComponents(true);
        initTableViews();
        initEventListener();
        initEventActionsMenu();
        initSignOutMenu();
        updateEventsFlowPane();
    }

    private void initTableViews() {
        tblClmnGroupName.setCellValueFactory(param -> param.getValue().nameProperty());
        tblClmnGroupPrice.setCellValueFactory(param -> param.getValue().priceProperty());
        tblClmnGroupCurrency.setCellValueFactory(param -> param.getValue().currencyProperty());

        tblClmAttFirstName.setCellValueFactory(param -> param.getValue().getNameProperty());
        tblClmAttEmail.setCellValueFactory(param -> param.getValue().getEmailProperty());
    }

    private void initEventListener() {
        lblEventVenue.setOnMouseClicked(event -> showingAddress.set(!showingAddress.get()));
        showingAddress.addListener((observable, oldValue, newValue) -> {
            if (showingAddress.get() && selectedEvent != null) {
                String address = selectedEvent.get().getLocation().getAddress();
                String zipcode = selectedEvent.get().getLocation().getZipCode();
                String city = selectedEvent.get().getLocation().getCity();
                lblEventVenue.setText(address + ", " + zipcode + " " + city);
            }
        });

        eventToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                hideDetailsPanelComponents(true);

            else hideDetailsPanelComponents(false);
        });

        selectedEvent.addListener((observable, oldValue, newValue) -> {

            if (newValue == null) {
                hideDetailsPanelComponents(true);
                return;
            }

            imgViewEvent.setImage(newValue.getEventImage());
            imgDetailBackground.setStyle("-fx-background-color: rgb(" + newValue.getColor().getRed() * 255 + ", " + newValue.getColor().getGreen() * 255 + ", " + newValue.getColor().getBlue() * 255 + ");");
            lblEventName.setText(newValue.getEventName());
            lblEventDateStart.setText(newValue.getStartDateTime().toLocalDate().toString());
            lblEventDateEnd.setText(newValue.getEndDateTime().toLocalDate().toString());
            lblEventTimeStart.setText(newValue.getStartDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            lblEventTimeEnd.setText(newValue.getEndDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            lblEventVenue.setText(newValue.getLocation().getVenueName());

            txtAreaInfo.setText(newValue.getDescription());

            lblSoldTickets.setText(newValue.getTicketsSold() + "");
            lblRemainingTickets.setText(newValue.getTicketsRemaining() + "");
            lblTotalTickets.setText(newValue.getTicketsTotal() + "");

            tblViewTicketGroup.setItems(newValue.getPriceGroups());
            hideDetailsPanelComponents(false);

            tblViewAttendees.setItems(newValue.getAttendeesList());

            /**
             *
             */
            //Wrap ObservableList of UserInfo in a FilteredList.
            FilteredList<UserInfo> filteredData = new FilteredList<>(DataManager.getInstance().getAllUsers(), b -> true);

            //Sets the filter predict when filter changes.
            txtFieldSearch.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                filteredData.setPredicate(user -> {

                    //If filter is empty, display all users.
                    if (newValue1 == null || newValue1.isEmpty())
                    {
                        return true;
                    }

                    //Compare user name with filter text.
                    String lowerCaseFilter = newValue1.toLowerCase();

                    if (user.getName().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    {
                        return true;
                    } else return false;

                });
            });

            SortedList<UserInfo> sortedUsers = new SortedList<>(filteredData);

            sortedUsers.comparatorProperty().bind(tblViewAttendees.comparatorProperty());

            tblViewAttendees.setItems(sortedUsers);
        });
    }

    /**
     * Fade animation for the event details panel. Fades out if no event is selected, fades in if an event is selected.
     *
     * @param hidden
     */

    private void hideDetailsPanelComponents(boolean hidden) {
        Timeline timeline = new Timeline();
        int endValue = 0;
        int duration = 300;
        if (hidden) {
            endValue = 0;
        } else if (!hidden) {
            endValue = 1;
        }

        KeyFrame op1 = new KeyFrame(Duration.millis(duration), new KeyValue(imgViewEvent.opacityProperty(), endValue));
        KeyFrame op2 = new KeyFrame(Duration.millis(duration), new KeyValue(imgDetailBackground.opacityProperty(), endValue));
        KeyFrame op3 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventName.opacityProperty(), endValue));
        KeyFrame op4 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateStart.opacityProperty(), endValue));
        KeyFrame op5 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventTimeStart.opacityProperty(), endValue));
        KeyFrame op12 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventTimeEnd.opacityProperty(), endValue));
        KeyFrame op6 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventVenue.opacityProperty(), endValue));
        KeyFrame op13 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventStartDateLabel.opacityProperty(), endValue));
        KeyFrame op14= new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateEndLabel.opacityProperty(), endValue));
        KeyFrame op15 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateEnd.opacityProperty(), endValue));

        KeyFrame op7 = new KeyFrame(Duration.millis(duration), new KeyValue(txtAreaInfo.opacityProperty(), endValue));
        KeyFrame op8 = new KeyFrame(Duration.millis(duration), new KeyValue(lblTotalTickets.opacityProperty(), endValue));
        KeyFrame op9 = new KeyFrame(Duration.millis(duration), new KeyValue(lblRemainingTickets.opacityProperty(), endValue));
        KeyFrame op11 = new KeyFrame(Duration.millis(duration), new KeyValue(lblSoldTickets.opacityProperty(), endValue));

        KeyFrame op10 = new KeyFrame(Duration.millis(duration), new KeyValue(tblViewTicketGroup.opacityProperty(), endValue));

        KeyFrame opLbl11 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateSpacer1.opacityProperty(), endValue));
        KeyFrame opLbl10 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateSpacer2.opacityProperty(), endValue));
        KeyFrame opLbl9 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateSpacer3.opacityProperty(), endValue));
        KeyFrame opLbl1 = new KeyFrame(Duration.millis(duration), new KeyValue(lblEventDateSpacer4.opacityProperty(), endValue));
        KeyFrame opLbl2 = new KeyFrame(Duration.millis(duration), new KeyValue(lblAtVenue.opacityProperty(), endValue));
        KeyFrame opLbl3 = new KeyFrame(Duration.millis(duration), new KeyValue(lblDescriptionHeader.opacityProperty(), endValue));
        KeyFrame opLbl4 = new KeyFrame(Duration.millis(duration), new KeyValue(lblRemaining.opacityProperty(), endValue));
        KeyFrame opLbl5 = new KeyFrame(Duration.millis(duration), new KeyValue(lblSold.opacityProperty(), endValue));
        KeyFrame opLbl7 = new KeyFrame(Duration.millis(duration), new KeyValue(lblTotal.opacityProperty(), endValue));
        KeyFrame opLbl6 = new KeyFrame(Duration.millis(duration), new KeyValue(lblTicketPricing.opacityProperty(), endValue));
        KeyFrame opImg7 = new KeyFrame(Duration.millis(duration), new KeyValue(imgViewTicketIcon.opacityProperty(), endValue));
        KeyFrame opBtn8 = new KeyFrame(Duration.millis(duration), new KeyValue(btnEventActions.opacityProperty(), endValue));

        KeyFrame tbl1 = new KeyFrame(Duration.millis(duration), new KeyValue(tblViewAttendees.opacityProperty(), endValue));


        timeline.getKeyFrames().addAll(op1, op2, op3, op4, op5, op6, op7, op8, op9, op10, op11, op12, op13, op14, op15, opLbl1, opLbl2, opLbl3, opLbl4, opLbl5, opLbl6, opLbl7, opLbl9, opLbl10, opLbl11,opImg7, opBtn8, tbl1);

        timeline.play();
    }

    /**
     * Opens the NewEventView.fxml. On closing, updates the list of events in flowPaneEvents with a list of all events.getEventTile.
     *
     * @param event
     */
    public void onCreate(ActionEvent event) {
        if (eventToggle.getSelectedToggle() != null)
            eventToggle.getSelectedToggle().setSelected(false);
        DataManager.getInstance().setSelectedEvent(null);

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

        stage.setOnHiding(event1 -> updateEventsFlowPane());
    }

    private void updateEventsFlowPane() {
        ObservableList toggles = FXCollections.observableArrayList();
        flowPaneEvents.getChildren().clear();

        ObservableList<Event> allEvents = null;
        try {
            allEvents = DataManager.getInstance().getAllEvents();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Event e : allEvents) {
            flowPaneEvents.getChildren().add(e.getEventTile());
            toggles.addAll(e.getEventTile());
        }

        eventToggle.getToggles().clear();
        eventToggle.getToggles().addAll(toggles);
    }

    public void onUser(MouseEvent mouseEvent) {
        //Location of the node
        double onScreenX = imgViewUser.getScene().getWindow().getX() + imgViewUser.getFitHeight() + imgViewUser.localToScene(imgViewUser.getBoundsInLocal()).getMinX();
        double onScreenY = imgViewUser.getScene().getWindow().getY() + imgViewUser.getFitWidth() + imgViewUser.localToScene(imgViewUser.getBoundsInLocal()).getMinY();

        double offsetX = imgViewUser.getFitWidth() * 2;
        double offsetY = imgViewUser.getFitHeight() * 0.8;

        //ContextMenu showed at the location of the button, with offsets applied
        signOutMenu.show(imgViewUser, onScreenX - offsetX, onScreenY + offsetY);
    }

    /**
     * Deletes the selected event.
     */
    public void onDeleteEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete the event?");
        alert.setContentText("This cannot be undone");
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DataManager.getInstance().deleteEvent(selectedEvent.get());
            updateEventsFlowPane();
        }
    }

    /**
     * Opens the EditEventView.fxml. Updates the event details panel upon closing.
     */
    public void onEditEvent() {
        if (DataManager.getInstance().getSelectedEvent() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No event selected");
            alert.setContentText("Please select an event to edit");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
        }

        Parent root = null;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/EditEventView.fxml")));
            stage.setTitle("Edit Event");
            stage.setMinWidth(511);
            stage.setMinHeight(737);
            stage.setScene(new Scene(root, 549, 750));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setOnHiding(event -> updateEventDetail());
    }

    /**
     * Re sets every component present in the details panel.
     */
    private void updateEventDetail() {
        imgViewEvent.setImage(selectedEvent.get().getEventImage());
        if (!selectedEvent.get().hasImage()) {
            imgDetailBackground.setStyle("-fx-background-color: rgb(" + selectedEvent.get().getColor().getRed() * 255 + ", " + selectedEvent.get().getColor().getGreen() * 255 + ", " + selectedEvent.get().getColor().getBlue() * 255 + ");");
        }
        else {
            imgDetailBackground.setStyle("-fx-background-color: #b2da41;");
        }
        lblEventName.setText(selectedEvent.get().getEventName());
        lblEventDateStart.setText(selectedEvent.get().getStartDateTime().toLocalDate().toString());
        lblEventTimeStart.setText(selectedEvent.get().getStartDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        lblEventTimeEnd.setText(selectedEvent.get().getEndDateTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        lblEventVenue.setText(selectedEvent.get().getLocation().getVenueName());

        txtAreaInfo.setText(selectedEvent.get().getDescription());

        lblSoldTickets.setText(selectedEvent.get().getTicketsSold() + "");
        lblRemainingTickets.setText(selectedEvent.get().getTicketsRemaining() + "");
        lblTotalTickets.setText(selectedEvent.get().getTicketsTotal() + "");

        tblViewTicketGroup.setItems(selectedEvent.get().getPriceGroups());
    }

    /**
     * Hides or shows the menu for attendee action (e.g. delete, edit, send ticket) through an animation.
     * Both the size, opacity, and rotation of the showHide button are rotated for a smooth look.
     *
     * @param event
     */
    public void onShowHideMenu(ActionEvent event) {
        Timeline timeline = new Timeline();
        Timeline opacity = new Timeline();
        if (tglBtnShowHideMenu.isSelected()) {
            //Rotation of the pressed button
            Timeline btnRotate = new Timeline(new KeyFrame(Duration.millis(100), new KeyValue(tglBtnShowHideMenu.rotateProperty(), -90)));

            //Opacity of the buttons inside the menu
            KeyFrame btn1KF = new KeyFrame(Duration.millis(100), new KeyValue(btnSendTicket.opacityProperty(), 1));
            KeyFrame btn2KF = new KeyFrame(Duration.millis(100), new KeyValue(btnDLTicket.opacityProperty(), 1));
            KeyFrame btn3KF = new KeyFrame(Duration.millis(100), new KeyValue(btnEditAttendeeInfo.opacityProperty(), 1));
            KeyFrame btn4KF = new KeyFrame(Duration.millis(100), new KeyValue(btnRemoveAttendee.opacityProperty(), 1));
            opacity.getKeyFrames().addAll(btn1KF, btn2KF, btn3KF, btn4KF);

            //Opacity for the menu container
            KeyFrame paneKF = new KeyFrame(Duration.millis(100), new KeyValue(anchorPaneAttendeeMenu.opacityProperty(), 1));
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
        } else {
            //Rotation of the pressed button
            Timeline btnRotate = new Timeline(new KeyFrame(Duration.millis(100), new KeyValue(tglBtnShowHideMenu.rotateProperty(), 0)));

            //Size of the menu container
            KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(anchorPaneAttendeeMenu.maxWidthProperty(), 0));
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
        DataManager.getInstance().removeUserFromEvent(tblViewAttendees.getSelectionModel().getSelectedItem(), selectedEvent.get());

        tblViewAttendees.setItems(selectedEvent.get().getAttendeesList());
    }

    /**
     * Shows a dropdown/contextmenu from the btnEventActions button.
     *
     * @param event
     */
    public void onEventActions(ActionEvent event) {
        //Location of the pressed button
        double onScreenX = btnEventActions.getScene().getWindow().getX() + btnEventActions.getHeight() + btnEventActions.localToScene(btnEventActions.getBoundsInLocal()).getMinX();
        double onScreenY = btnEventActions.getScene().getWindow().getY() + btnEventActions.getWidth() + btnEventActions.localToScene(btnEventActions.getBoundsInLocal()).getMinY();

        double offsetX = btnEventActions.getWidth() * 2;
        double offsetY = btnEventActions.getHeight() * 1.5;

        //ContextMenu showed at the location of the button, with offsets applied
        eventActionsMenu.show(btnEventActions, onScreenX - offsetX, onScreenY + offsetY);
    }

    private void signOut() {
        //TODO: Temp?
        Parent root = null;
        Stage thisStage = (Stage) lblUser.getScene().getWindow();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/SignIn.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        thisStage.setScene(new Scene(root));

    }


    private void initEventActionsMenu() {
        MenuItem editEvent = new MenuItem("Edit Event");
        editEvent.setOnAction(event -> onEditEvent());
        MenuItem deleteEvent = new MenuItem("Delete Event");
        deleteEvent.setOnAction(event -> onDeleteEvent());


        eventActionsMenu = new ContextMenu(editEvent, deleteEvent);
        eventActionsMenu.setAutoHide(true);
    }

    private void initSignOutMenu() {
        MenuItem signOut = new MenuItem("Sign out");
        signOut.setOnAction(event -> signOut());

        signOutMenu = new ContextMenu(signOut);
        signOutMenu.setAutoHide(true);
    }

    public void onSaveAttendeesList(ActionEvent event) throws SQLServerException, IOException {
        if (selectedEvent.get() != null) {
            expoList.createListOfAttendees(selectedEvent.get());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "A list has been made.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
        }
    }

    /**
     * Opens the Add Attendees menu if event is selected, else warns that an event must be selected.
     */
    public void btnAddAttendeesMenu(ActionEvent actionEvent)
    {
        if (DataManager.getInstance().getSelectedEvent() != null)
        {
            Parent root = null;
            Stage stage = new Stage();
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/addAttendees.fxml")));
                stage.setTitle("Add Attendees");
                stage.setMinWidth(511);
                stage.setMinHeight(737);
                stage.setScene(new Scene(root, 849, 650));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an event.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
        }
    }

    public void updateList(ActionEvent actionEvent) {
        selectedEvent.get().setAttendeesList((ObservableList<UserInfo>) searcher.search(selectedEvent.get().getAttendeesList(), txtFieldSearch.getText()));
        updateEventDetail();
        tblViewAttendees.refresh();
    }
}

package gui.controllers;

import be.Event;
import be.PriceGroup;
import be.Venue;
import bll.DataManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EditEventController implements Initializable {

    @FXML public Button btnNewPriceGroup;
    @FXML public Button btnDeletePriceGroup;
    @FXML public ImageView imgViewEvent;
    @FXML public Button btnAddImage;
    @FXML public ColorPicker colorPicker;
    @FXML public TextField txtFieldEventName;
    @FXML public RadioButton radioButtonImage;
    @FXML public RadioButton radioButtonColor;
    @FXML public DatePicker datePickerStartDate;
    @FXML public TextField txtFieldStartTime;
    @FXML public DatePicker datePickerEndDate;
    @FXML public TextField txtFieldEndTime;
    @FXML public TableView<Venue> tblViewVenues;
    @FXML public TableColumn<Venue, String> tblClmVenueName;
    @FXML public TableColumn<Venue, String> tblClmVenueAddress;
    @FXML public TableColumn<Venue, String> tblClmVenueZipcode;
    @FXML public TableColumn<Venue, String> tblClmVenueCity;
    @FXML public Button btnNewVenue;
    @FXML public Button btnDeleteVenue;
    @FXML public TextArea txtAreaEditEventInfo;
    @FXML public TextField txtFieldTicketRemaining;
    @FXML public TextField txtFieldTicketsSold;
    @FXML public TableView<PriceGroup> tblViewNewEventTicketGroup;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupName;
    @FXML public TableColumn<PriceGroup, Number> tblClmnGroupPrice;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupCurrency;
    @FXML public Button btnSaveEditEvent;
    @FXML public Button btnEditVenue;
    @FXML public Button btnEditPriceGroup;

    private ToggleGroup imageColorToggleGroup;
    private Event editedEvent;
    private ObservableList<PriceGroup> priceGroups;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editedEvent = DataManager.getInstance().getSelectedEvent();

        priceGroups = editedEvent.getPriceGroups();
        DataManager.getInstance().setPriceGroupList(priceGroups);

        txtFieldTicketRemaining.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));
        txtFieldTicketsSold.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));
        txtFieldTicketsSold.setOnMouseClicked(event -> txtFieldTicketsSold.clear());
        txtFieldTicketRemaining.setOnMouseClicked(event -> txtFieldTicketRemaining.clear());

        txtFieldStartTime.setTextFormatter(dateTextFormatter());
        txtFieldEndTime.setTextFormatter(dateTextFormatter());


        initTableViews();
        initImageView();
        initRadioBtnListener();
        initValues();
        colorPicker.setValue(Color.DARKGREEN);
    }

    private void initValues(){
        imgViewEvent.setImage(editedEvent.getEventImage());
        txtFieldEventName.setText(editedEvent.getEventName());

        datePickerStartDate.setValue(editedEvent.getStartDateTime().toLocalDate());
        txtFieldStartTime.setText(editedEvent.getStartDateTime().toLocalTime().toString());

        if (editedEvent.getEndDateTime() != null) {
            datePickerEndDate.setValue(editedEvent.getEndDateTime().toLocalDate());
            txtFieldEndTime.setText(editedEvent.getEndDateTime().toLocalTime().toString());
        }
        if (editedEvent.getColor() != null){
            colorPicker.setValue(editedEvent.getColor());
        }

        txtAreaEditEventInfo.setText(editedEvent.getDescription());
        txtFieldTicketRemaining.setText(editedEvent.getTicketsTotal()+"");
        txtFieldTicketsSold.setText(editedEvent.getTicketsSold() + "");

        imgViewEvent.setImage(editedEvent.getEventImage());

        if (editedEvent.hasImage()){
            imageColorToggleGroup.selectToggle(radioButtonImage);
        }
        else {
            imageColorToggleGroup.selectToggle(radioButtonColor);
            colorPicker.setValue(editedEvent.getColor());
        }

    }

    private void initImageView(){
        Rectangle clip = new Rectangle(imgViewEvent.getFitWidth(), imgViewEvent.getFitHeight());
        clip.setArcHeight(9);
        clip.setArcWidth(9);
        clip.setStroke(Color.TRANSPARENT);
        imgViewEvent.setClip(clip);
    }

    private void initRadioBtnListener(){
        imageColorToggleGroup = new ToggleGroup();
        imageColorToggleGroup.getToggles().add(radioButtonColor);
        imageColorToggleGroup.getToggles().add(radioButtonImage);

        imageColorToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null){
                imageColorToggleGroup.selectToggle(radioButtonColor);
            }

            if (newValue == radioButtonColor){
                colorPicker.setValue(Color.DARKGREEN);
                imgViewEvent.setImage(generateBlankImage(colorPicker.getValue()));
                btnAddImage.setDisable(true);
                btnAddImage.setOpacity(0);
                colorPicker.setDisable(false);
                colorPicker.setOpacity(1);
            }
            else if (newValue == radioButtonImage){
                colorPicker.setDisable(true);
                colorPicker.setOpacity(0);
                btnAddImage.setDisable(false);
                btnAddImage.setOpacity(1);
            }
        });
    }

    public void onColorPicker(ActionEvent event) {
        imgViewEvent.setImage(generateBlankImage(colorPicker.getValue()));
    }

    /**
     * Opens a filechooser to choose a basic image file of the .png or .jpg type.
     * Sets the imageView accordingly to the chosen file.
     * @param event
     */
    public void onAddImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Standard image files", "*.png", "*.jpg", "*.jpeg"));
        File imageFile = fileChooser.showOpenDialog((Stage) btnAddImage.getScene().getWindow());
        Image image = new Image(imageFile.getAbsolutePath());
        imgViewEvent.setImage(image);
        imgViewEvent.setFitWidth(367);
        imgViewEvent.setFitHeight(81);
    }

    /**
     * Generates a static image of the given color, which can then be scaled to the imageview.
     * @param color
     * @return
     */
    private Image generateBlankImage(Color color) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();
        pw.setColor(0, 0, color);
        return img ;
    }

    private void initTableViews(){
        tblClmVenueName.setCellValueFactory(param -> param.getValue().getVenueNameProperty());
        tblClmVenueAddress.setCellValueFactory(param -> param.getValue().getAddressProperty());
        tblClmVenueZipcode.setCellValueFactory(param -> param.getValue().getZipCodeProperty());
        tblClmVenueCity.setCellValueFactory(param -> param.getValue().getCityProperty());

        tblClmnGroupName.setCellValueFactory(param -> param.getValue().nameProperty());
        tblClmnGroupPrice.setCellValueFactory(param -> param.getValue().priceProperty());
        tblClmnGroupCurrency.setCellValueFactory(param -> param.getValue().currencyProperty());

        try {
            tblViewVenues.setItems(DataManager.getInstance().getAllVenues());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblViewVenues.getSelectionModel().select(editedEvent.getLocation());

        tblViewNewEventTicketGroup.setItems(priceGroups);

    }


    /** Opens the newVenue windows. Updates the list of venues upon closing.
     */
    public void onNewVenue(ActionEvent event) {
        openStage("createVenue.fxml", "New Venue", new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    tblViewVenues.setItems(DataManager.getInstance().getAllVenues());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /** Deletes the selected venue from both the tableView list and the Database.
     */
    public void onDeleteVenue(ActionEvent event) {
        if (tblViewVenues.getSelectionModel().getSelectedItem() != null) {
            Venue selectedItem = tblViewVenues.getSelectionModel().getSelectedItem();
            DataManager.getInstance().removeVenue(selectedItem);

            //TODO: TEMP
            tblViewVenues.getItems().remove(selectedItem);
        }
    }

    /** Opens the EditVenue windows. Updates the selected Venue upon closing.
     */
    public void onEditVenue(ActionEvent event) {
        if (!tblViewVenues.getItems().isEmpty() && tblViewVenues.getSelectionModel().getSelectedItem() != null) {
            DataManager.getInstance().setSelectedVenue(tblViewVenues.getSelectionModel().getSelectedItem());
            openStage("editVenue.fxml", "Edit Venue", new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                }
            });
        }
    }

    /** Opens the NewPriceGroup windows. Updates the list of pricegroups upon closing.
     */
    public void onNewPriceGroup(ActionEvent event) {
        openStage("createPriceGroup.fxml", "New Price Group", new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                priceGroups = DataManager.getInstance().getPriceGroupList(editedEvent);
            }
        });

    }

    /** Deletes the selected pricegroup.
     */
    public void OnDeletePriceGroup(ActionEvent event) {
        if (tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem() != null) {
            PriceGroup selectedItem = tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem();
            DataManager.getInstance().removePriceGroup(editedEvent, selectedItem);
            tblViewNewEventTicketGroup.setItems(DataManager.getInstance().getPriceGroupList(editedEvent));
        }
    }

    /** Opens the EditPriceGroup windows. Updates the selected pricegroup upon closing.
     */
    public void OnEditPriceGroup(ActionEvent event) {
        if (!tblViewNewEventTicketGroup.getItems().isEmpty() && tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem() != null) {
            DataManager.getInstance().setSelectedPriceGroup(tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem());
            openStage("editPriceGroup.fxml", "Edit Price Group", new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    DataManager.getInstance().updatePriceGroup(DataManager.getInstance().getSelectedPriceGroup());
                }
            });
        }
    }

    /**
     * Saves every parameter of the event, ensuring all changes made are updated.
     * @param event
     */
    public void onSaveEdit(ActionEvent event) {
        if (tblViewVenues.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No Venue selected");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
            return;
        }
        if (tblViewNewEventTicketGroup.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No Ticket Groups added");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
            return;
        }

        if (datePickerStartDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No start date chosen");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
            return;
        }

        try {
            //Event Name
            editedEvent.setEventName(txtFieldEventName.getText());

            //Dates and times
            LocalDateTime[] times = saveDate();
            editedEvent.setStartDateTime(times[0]);
            editedEvent.setEndDateTime(times[1]);

            //Venue
            Venue venue = tblViewVenues.getSelectionModel().getSelectedItem();
            editedEvent.setLocation(venue);

            //Tickets
            int ticketSold = Integer.parseInt(txtFieldTicketsSold.getText());
            editedEvent.setTicketsSold(ticketSold);
            int ticketsRemaining = Integer.parseInt(txtFieldTicketRemaining.getText());
            editedEvent.setTicketsTotal(ticketsRemaining);

            //PriceGroups
            editedEvent.setPriceGroups(tblViewNewEventTicketGroup.getItems());

            //Description
            String description = txtAreaEditEventInfo.getText();
            editedEvent.setDescription(description);

            //Image and Color
            saveEditImageAndColor();


            DataManager.getInstance().updateEvent(editedEvent);

            ((Node) (event.getSource())).getScene().getWindow().hide();


        }

        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program ran into an error. Please check your input values.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gui/styles/mainStylesheet.css")).toExternalForm());
            alert.show();
        }
    }


    /**
     * Gets the dateTime of the new event object;
     * @return an array of startDateTime at [1], and endDateTime at [2]
     */
    private LocalDateTime[] saveDate(){
        //Start DateTime
        LocalDateTime startDateTime = DateTimeExtract(datePickerStartDate, txtFieldStartTime);

        //End DateTime
        LocalDateTime endDateTime = DateTimeExtract(datePickerEndDate, txtFieldEndTime);

        LocalDateTime[] times = {startDateTime, endDateTime};
        return times;
    }

    /**
     * Extracts a LocalDateTime object from a TextField with a valid time input and a datepicker.
     * @param datePicker
     * @param timeField
     * @return
     */
    private LocalDateTime DateTimeExtract(DatePicker datePicker, TextField timeField) {
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.of(0, 0);
        if (timeField.getText() != null && !timeField.getText().isEmpty() && !timeField.getText().isBlank())

        {
            time = LocalTime.parse(timeField.getText());
        }

        LocalDateTime dateTime = date.atTime(time);

        return dateTime;
    }

    /**
     * Gets the image and color of the new event.
     * @return an array of color at [1], and image at [2]
     */
    private void saveEditImageAndColor(){
        Color color = null;
        Image image = null;

        if (imageColorToggleGroup.getSelectedToggle().equals(radioButtonColor)){
            color = colorPicker.getValue();
            image = generateBlankImage(color);
            editedEvent.setColor(color);
            editedEvent.setEventImage(image);
            editedEvent.setHasImage(false);

        }
        else if (imageColorToggleGroup.getSelectedToggle() == radioButtonImage) {
            color = Color.TRANSPARENT;
            image = imgViewEvent.getImage();
            editedEvent.setColor(color);
            editedEvent.setEventImage(image);
            editedEvent.setHasImage(true);
        }
    }

    /**
     * Utility function to open a new transparent stage as a pop-up windows, with a defined onHiding event.
     * @param fxml
     * @param title
     * @param event
     */
    private void openStage(String fxml, String title, EventHandler<WindowEvent> event) {
        Parent root = null;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/" + fxml)));
            stage.setTitle(title);
            stage.setMinWidth(264);
            stage.setMinHeight(271);
            stage.setMaxWidth(264);
            stage.setMaxHeight(271);
            stage.setScene(new Scene(root, 264, 274));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

            stage.setOnHiding(event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * An interger filter, for use in a textFormatter. Only allows whole numbers.
     * @return
     */
    private UnaryOperator<TextFormatter.Change> integerFilter(){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            //if (newText.matches("-?([1-9][0-9]*)?")) {
            if (newText.matches("-?([1-9][0-9]*)?")) {

                return change;
            }
            return null;
        };

        return integerFilter;
    }

    private TextFormatter dateTextFormatter(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            TextFormatter textFormatter = new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00"));
            return textFormatter;
        } catch (ParseException e) {
            e.printStackTrace();
            return new TextFormatter(new DateTimeStringConverter());
        }
    }

}

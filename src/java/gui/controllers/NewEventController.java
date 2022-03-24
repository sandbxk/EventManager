package gui.controllers;

import be.PriceGroup;
import be.Venue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewEventController implements Initializable {
    
    @FXML public Button btnNewPriceGroup;
    @FXML public Button btnDeletePriceGroup;
    @FXML public ImageView imgViewEvent;
    @FXML public ColorPicker colorPicker;
    @FXML public TextField txtFieldEventName;
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
    @FXML public TextArea txtAreaNewEventInfo;
    @FXML public TextField txtFieldTicketRemaining;
    @FXML public TextField txtFieldTicketsSold;
    @FXML public TableView<PriceGroup> tblViewNewEventTicketGroup;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupName;
    @FXML public TableColumn<PriceGroup, Number> tblClmnGroupPrice;
    @FXML public TableColumn<PriceGroup, String> tblClmnGroupCurrency;
    @FXML public Button btnSaveNewEvent;
    @FXML public Button btnEditVenue;
    @FXML public Button btnEditPriceGroup;

    @FXML public TextField txtFieldNewVenueCity;
    @FXML public TextField txtFieldNewVenueName;
    @FXML public TextField txtFieldNewVenueAddress;
    @FXML public TextField txtFieldNewVenueZipcode;

    @FXML public TextField txtFieldEditVenueName;
    @FXML public TextField txtFieldEditVenueAddress;
    @FXML public TextField txtFieldEditVenueZipcode;
    @FXML public TextField txtFieldEditVenueCity;

    @FXML public TextField txtFieldNewPriceGroupName;
    @FXML public TextField txtFieldNewPriceGroupPrice;
    @FXML public TextField txtFieldNewPriceGroupCurrency;

    @FXML public TextField txtFieldEditPriceGroupName;
    @FXML public TextField txtFieldEditPriceGroupPrice;
    @FXML public TextField txtFieldEditPriceGroupCurrency;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init for Edit PriceGroup
        if (txtFieldEditVenueName == null && txtFieldNewVenueName == null
                && txtFieldEditPriceGroupName != null && txtFieldNewPriceGroupName == null) {
            txtFieldEditPriceGroupPrice.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        }

        //Init for New PriceGroup
        if (txtFieldEditVenueName == null && txtFieldNewVenueName == null
                && txtFieldEditPriceGroupName == null && txtFieldNewPriceGroupName != null) {
            txtFieldNewPriceGroupPrice.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        }

        //Init for Edit Venue
        if (txtFieldEditVenueName != null && txtFieldNewVenueName == null
                && txtFieldEditPriceGroupName == null && txtFieldNewPriceGroupName == null) {

        }

        //Init for New Venue
        if (txtFieldEditVenueName == null && txtFieldNewVenueName == null
                && txtFieldEditPriceGroupName != null && txtFieldNewPriceGroupName == null) {

        }

        //Init for New Event
        if (txtFieldEditVenueName == null && txtFieldNewVenueName == null
                && txtFieldEditPriceGroupName == null && txtFieldNewPriceGroupName == null) {
            initTableViews();
            initImageView();
        }
    }

    private void initImageView(){
        Rectangle clip = new Rectangle(imgViewEvent.getFitWidth(), imgViewEvent.getFitHeight());
        clip.setArcHeight(9);
        clip.setArcWidth(9);
        clip.setStroke(Color.TRANSPARENT);
        imgViewEvent.setClip(clip);
    }

    public void onColorPicker(ActionEvent event) {
    imgViewEvent.setImage(generateBlankImage(colorPicker.getValue()));
    }

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
    }


    public void onNewVenue(ActionEvent event) {
        openStage("createVenue.fxml", "New Venue");
        tblViewVenues.getItems().add(new Venue("fe", "f", "f", "f"));
    }

    public void onDeleteVenue(ActionEvent event) {
        Venue selctedItem = tblViewVenues.getSelectionModel().getSelectedItem();
        tblViewVenues.getItems().remove(selctedItem);
    }

    public void onEditVenue(ActionEvent event) {
        if (!tblViewVenues.getItems().isEmpty() && tblViewVenues.getSelectionModel().getSelectedItem() != null) {
            openStage("editVenue.fxml", "Edit Venue");
            txtFieldEditVenueName.setText(tblViewVenues.getSelectionModel().getSelectedItem().getVenueName());
            txtFieldEditVenueAddress.setText(tblViewVenues.getSelectionModel().getSelectedItem().getAddress());
            txtFieldEditVenueZipcode.setText(tblViewVenues.getSelectionModel().getSelectedItem().getZipCode());
            txtFieldEditVenueCity.setText(tblViewVenues.getSelectionModel().getSelectedItem().getCity());
        }
    }


    public void onNewPriceGroup(ActionEvent event) {
        openStage("createPriceGroup.fxml", "New Price Group");
        tblViewNewEventTicketGroup.getItems().add(new PriceGroup("n", 1, "DKK"));
    }

    public void OnDeletePriceGroup(ActionEvent event) {
        PriceGroup selectedItem = tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem();
        tblViewNewEventTicketGroup.getItems().remove(selectedItem);
    }

    public void OnEditPriceGroup(ActionEvent event) {
        if (!tblViewNewEventTicketGroup.getItems().isEmpty() && tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem() != null)
        openStage("editPriceGroup.fxml", "Edit Price Group");
        txtFieldEditPriceGroupName.setText(tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem().getName());
        txtFieldEditPriceGroupPrice.setText(String.valueOf(tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem().getPrice()));
        txtFieldEditPriceGroupCurrency.setText(tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem().getCurrency());
    }

    public void onSave(ActionEvent event) {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public void onSaveNewVenue(ActionEvent event) {

        Venue venue = new Venue("Unnamed", "---", "---", "---");

        if (txtFieldNewVenueName.getText() != null && !txtFieldNewVenueName.getText().isEmpty() && !txtFieldNewVenueName.getText().isBlank())
        {
            String name = txtFieldNewPriceGroupName.getText();
            venue.setVenueName(name);
        }
        if (txtFieldNewVenueAddress.getText() != null && !txtFieldNewVenueAddress.getText().isEmpty() && !txtFieldNewVenueAddress.getText().isBlank())
        {
            String address = txtFieldNewVenueAddress.getText();
            venue.setAddress(address);
        }
        if (txtFieldNewVenueZipcode.getText() != null && !txtFieldNewVenueZipcode.getText().isEmpty() && !txtFieldNewVenueZipcode.getText().isBlank()) {
            String zipcode = txtFieldNewVenueZipcode.getText();
            venue.setZipCode(zipcode);
        }
        if (txtFieldNewVenueCity.getText() != null && !txtFieldNewVenueCity.getText().isEmpty() && !txtFieldNewVenueCity.getText().isBlank()) {
            String city = txtFieldNewVenueCity.getText();
            venue.setCity(city);
        }

        tblViewVenues.getItems().add(venue);

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelNewVenue(ActionEvent event) {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }



    public void onSaveEditVenue(ActionEvent event) {
        Venue editedVenue = tblViewVenues.getSelectionModel().getSelectedItem();

        editedVenue.setVenueName(txtFieldEditVenueName.getText());
        editedVenue.setAddress(txtFieldEditVenueAddress.getText());
        editedVenue.setZipCode(txtFieldEditVenueZipcode.getText());
        editedVenue.setCity(txtFieldEditVenueCity.getText());

        //DataManger.getInstance.updateVenue(editedVenue)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelEditVenue(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public void onSaveNewPriceGroup(ActionEvent event) {

        PriceGroup newPriceGroup = new PriceGroup("Blank", 0, "DKK");

        if (txtFieldNewPriceGroupName.getText() != null && !txtFieldNewPriceGroupName.getText().isEmpty() && !txtFieldNewPriceGroupName.getText().isBlank())
        {
            String name = txtFieldNewPriceGroupName.getText();
            newPriceGroup.setName(name);
        }
        if (txtFieldNewPriceGroupPrice.getText() != null && !txtFieldNewPriceGroupPrice.getText().isEmpty() && !txtFieldNewPriceGroupPrice.getText().isBlank())
        {
            int price = Integer.parseInt(txtFieldNewPriceGroupPrice.getText());
            newPriceGroup.setPrice(price);
        }
        if (txtFieldNewPriceGroupCurrency.getText() != null && !txtFieldEditPriceGroupCurrency.getText().isEmpty() && !txtFieldEditPriceGroupCurrency.getText().isBlank()) {
            String currency = txtFieldNewPriceGroupCurrency.getText();
            newPriceGroup.setCurrency(currency);
        }

        tblViewNewEventTicketGroup.getItems().add(newPriceGroup);

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelNewPriceGroup(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public void onSaveEditPriceGroup(ActionEvent event) {
        PriceGroup priceGroup = tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem();

        priceGroup.setName(txtFieldEditPriceGroupName.getText());
        priceGroup.setPrice(Integer.parseInt(txtFieldEditPriceGroupPrice.getText()));
        priceGroup.setCurrency(txtFieldEditPriceGroupCurrency.getText());

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelEditPriceGroup(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void openStage(String fxml, String title) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

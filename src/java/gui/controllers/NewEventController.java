package gui.controllers;

import be.PriceGroup;
import be.Venue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle clip = new Rectangle(imgViewEvent.getFitWidth(), imgViewEvent.getFitHeight());
        clip.setArcHeight(9);
        clip.setArcWidth(9);
        clip.setStroke(Color.TRANSPARENT);
        imgViewEvent.setClip(clip);

        initTableViews();
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
        tblViewVenues.getItems().add(new Venue("fe", "f", "f", "f"));
    }

    public void onDeleteVenue(ActionEvent event) {
    }

    public void onNewPriceGroup(ActionEvent event) {
    }

    public void OnDeletePriceGroup(ActionEvent event) {
    }

    public void onSave(ActionEvent event) {


    }
}

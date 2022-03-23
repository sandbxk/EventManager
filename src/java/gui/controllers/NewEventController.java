package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    @FXML public TableView tblViewVenues;
    @FXML public TableColumn tblClmVenueName;
    @FXML public TableColumn tblClmVenueAddress;
    @FXML public TableColumn tblClmVenueZipcode;
    @FXML public TableColumn tblClmVenueCity;
    @FXML public Button btnNewVenue;
    @FXML public Button btnDeleteVenue;
    @FXML public TextArea txtAreaNewEventInfo;
    @FXML public TextField txtFieldTicketRemaining;
    @FXML public TextField txtFieldTicketsSold;
    @FXML public TableView tblViewNewEventTicketGroup;
    @FXML public TableColumn tblClmnGroupPrice;
    @FXML public Button btnSaveNewEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onColorPicker(ActionEvent event) {
    }

    public void onSave(ActionEvent event) {
    }
}

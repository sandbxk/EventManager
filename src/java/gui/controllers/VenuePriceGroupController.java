package gui.controllers;

import be.PriceGroup;
import be.Venue;
import bll.DataManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class VenuePriceGroupController implements Initializable {

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
        if (txtFieldEditPriceGroupName != null) {
            txtFieldEditPriceGroupPrice.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));

            PriceGroup selectedPriceGroup = DataManager.getInstance().getSelectedPriceGroup();

            txtFieldEditPriceGroupName.setText(selectedPriceGroup.getName());
            txtFieldEditPriceGroupPrice.setText(String.valueOf(selectedPriceGroup.getPrice()));
            txtFieldEditPriceGroupCurrency.setText(selectedPriceGroup.getCurrency());
        }

        //Init for New PriceGroup
        if (txtFieldNewPriceGroupName != null) {
            txtFieldNewPriceGroupPrice.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));
        }

        //Init for Edit Venue
        if (txtFieldEditVenueName != null) {
            txtFieldEditVenueZipcode.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));

            Venue selectedVenue = DataManager.getInstance().getSelectVenue();

            txtFieldEditVenueName.setText(selectedVenue.getVenueName());
            txtFieldEditVenueAddress.setText(selectedVenue.getAddress());
            txtFieldEditVenueZipcode.setText(selectedVenue.getZipCode());
            txtFieldEditVenueCity.setText(selectedVenue.getCity());
        }

        //Init for New Venue
        if (txtFieldNewVenueName != null) {
            txtFieldNewVenueZipcode.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));
        }
    }

    public void onSaveNewVenue(ActionEvent event) {

        Venue venue = new Venue("Unnamed", "---", "---", "---");

        if (txtFieldNewVenueName.getText() != null && !txtFieldNewVenueName.getText().isEmpty() && !txtFieldNewVenueName.getText().isBlank())
        {
            String name = txtFieldNewVenueName.getText();
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

        DataManager.getInstance().newVenue(venue);

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelNewVenue(ActionEvent event) {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }



    public void onSaveEditVenue(ActionEvent event) {
        Venue editedVenue = DataManager.getInstance().getSelectVenue();

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
        if (txtFieldNewPriceGroupCurrency.getText() != null && !txtFieldNewPriceGroupCurrency.getText().isEmpty() && !txtFieldNewPriceGroupCurrency.getText().isBlank()) {
            String currency = txtFieldNewPriceGroupCurrency.getText();
            newPriceGroup.setCurrency(currency);
        }

        DataManager.getInstance().newPriceGroup(null, newPriceGroup);

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelNewPriceGroup(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public void onSaveEditPriceGroup(ActionEvent event) {
        PriceGroup editedPriceGroup = DataManager.getInstance().getSelectedPriceGroup();

        editedPriceGroup.setName(txtFieldEditPriceGroupName.getText());
        editedPriceGroup.setPrice(Integer.parseInt(txtFieldEditPriceGroupPrice.getText()));
        editedPriceGroup.setCurrency(txtFieldEditPriceGroupCurrency.getText());

        //DataManger.getInstance.updatePriceGroup(editedPriceGroup)

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void onCancelEditPriceGroup(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


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
}

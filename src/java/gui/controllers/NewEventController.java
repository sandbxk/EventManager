package gui.controllers;

import be.PriceGroup;
import be.Venue;
import bll.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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

    private ObservableList<PriceGroup> priceGroups;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priceGroups = FXCollections.observableArrayList();
        DataManager.getInstance().setPriceGroups(priceGroups);

        txtFieldTicketRemaining.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));
        txtFieldTicketsSold.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter()));

        initTableViews();
        initImageView();
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


        tblViewVenues.setItems(DataManager.getInstance().getAllVenues());
        tblViewNewEventTicketGroup.setItems(priceGroups);

    }


    public void onNewVenue(ActionEvent event) {
        openStage("createVenue.fxml", "New Venue", new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tblViewVenues.setItems(DataManager.getInstance().getAllVenues());
            }
        });

    }

    public void onDeleteVenue(ActionEvent event) {
        if (tblViewVenues.getSelectionModel().getSelectedItem() != null) {
            Venue selectedItem = tblViewVenues.getSelectionModel().getSelectedItem();
            DataManager.getInstance().removeVenue(selectedItem);

            //TODO: TEMP
            tblViewVenues.getItems().remove(selectedItem);
        }
    }

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


    public void onNewPriceGroup(ActionEvent event) {
        openStage("createPriceGroup.fxml", "New Price Group", new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                priceGroups = DataManager.getInstance().getPriceGroups(null);
            }
        });

    }

    public void OnDeletePriceGroup(ActionEvent event) {
        if (tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem() != null) {
            PriceGroup selectedItem = tblViewNewEventTicketGroup.getSelectionModel().getSelectedItem();
            DataManager.getInstance().removePriceGroup(null, selectedItem);
            tblViewNewEventTicketGroup.setItems(DataManager.getInstance().getPriceGroups(null));
        }
    }

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

    public void onSave(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

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

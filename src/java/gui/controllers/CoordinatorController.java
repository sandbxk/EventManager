package gui.controllers;

import gui.component.SizeControlledContextMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {


    @FXML public TextField txtFieldSearch;
    @FXML public Button btnSearch;
    @FXML public Button btnCreateActions;


    ContextMenu creationMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCreationMenu();
    }

    public void onCreate(ActionEvent event) {
        creationMenu.show(btnCreateActions, Side.BOTTOM, btnCreateActions.getLayoutX(), btnCreateActions.getLayoutY());
    }
    
    private void initCreationMenu(){
        this.creationMenu = new SizeControlledContextMenu();
        MenuItem eventCreator = new MenuItem();
        eventCreator.setOnAction(event -> createEvent());
        eventCreator.setText("Create Event");

        MenuItem venueCreator = new MenuItem();
        venueCreator.setOnAction(event -> createVenue());
        venueCreator.setText("Create Venue");

        double height = 50;
        double width = 50;
        creationMenu.setHeight(height);
        creationMenu.setMaxSize(width, height);

        creationMenu.getItems().add(eventCreator);
        creationMenu.getItems().add(venueCreator);


    }
    
    private void createEvent(){
        
    }
    
    private void createVenue(){
        
    }
}

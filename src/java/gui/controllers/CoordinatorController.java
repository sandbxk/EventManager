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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCreate(ActionEvent event) {

    }

}

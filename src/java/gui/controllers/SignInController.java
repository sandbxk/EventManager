package gui.controllers;

import be.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML BorderPane borderPaneRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Event event = new Event();
        borderPaneRoot.setCenter(event.getNode());
    }

}

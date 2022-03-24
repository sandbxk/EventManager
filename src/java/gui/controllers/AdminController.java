package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminController {

    public void onCreate(ActionEvent event) throws IOException {
        Parent root = null;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/views/NewCoordinatorView.fxml")));
            stage.setTitle("New Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



package gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewCoordinatorController {
    public TextField txtLastName;
    public TextField txtUserName;
    public TextField txtPassword;
    public Button btnSubmit;
    public Button btnCancel;

    public void handleSubmit(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}

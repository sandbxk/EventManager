package gui.controllers;

import be.EUserType;
import be.UserInfo;
import dal.CoordinatorDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewCoordinatorController {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnCancel;

    private CoordinatorDAO corDAO;

    public NewCoordinatorController() throws IOException {
        corDAO = new CoordinatorDAO();
    }

    public void handleSubmit(ActionEvent actionEvent) {

        if(!txtFirstName.getText().isEmpty() && !txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty())
        {
            String name = "" + txtFirstName.getText() + " " + txtLastName.getText();
            UserInfo user = new UserInfo(1, name, EUserType.EVENT_COORDINATOR);
            user.setName(name);
            corDAO.create(user,txtUserName.getText(),txtPassword.getText());
            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}

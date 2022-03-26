package gui.controllers;

import bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewUserController {

    @FXML
    private TextField txtFieldNewUserName;
    @FXML
    private TextField txtFieldNewUserLogin;
    @FXML
    private TextField txtFieldNewPassword;
    @FXML
    private TextField txtFieldNewUserEmail;
    @FXML
    private TextField txtWarning;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;



    public void onSaveNewUser(ActionEvent actionEvent)
    {
        if (txtFieldNewUserName.getText().isEmpty())
        {
            txtWarning.setText("User Name cannot be empty.");
        }

        if (txtFieldNewUserLogin.getText().isEmpty())
        {
            txtWarning.setText(txtWarning.getText() + " " + "Login cannot be empty.");
        }

        if (txtFieldNewPassword.getText().isEmpty())
        {
            txtWarning.setText(txtWarning.getText() + " " + "Password cannot be empty.");
        }

        if (txtFieldNewUserEmail.getText().isEmpty())
        {
            txtWarning.setText(txtWarning.getText() + " " + "Email cannot be empty.");
        }

        if (
                !txtFieldNewUserName.getText().isEmpty() &&
                !txtFieldNewUserLogin.getText().isEmpty() &&
                !txtFieldNewPassword.getText().isEmpty() &&
                !txtFieldNewUserEmail.getText().isEmpty()
            )
        {
            DataManager.getInstance().createNewUser(
                    txtFieldNewUserName.getText(),
                    txtFieldNewUserLogin.getText(),
                    txtFieldNewPassword.getText(),
                    txtFieldNewUserEmail.getText()
            );

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
    }

    public void onCancelNewUser(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}

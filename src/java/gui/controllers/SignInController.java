package gui.controllers;

import be.EUserType;
import be.Event;
import bll.DatabaseAuthenticator;
import bll.interfaces.IAuthenticator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML public Button loginSubmitBtn;
    @FXML public TextField passwordField;
    @FXML public TextField usernameField;

    @FXML BorderPane borderPaneRoot;

    IAuthenticator authenticator;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        authenticator = new DatabaseAuthenticator();
    }

    public void onLogin(ActionEvent actionEvent)
    {
        loginAsCoordinator();


        if (false && authenticator.authenticate(usernameField.getText(), passwordField.getText()))
        {
            switch (authenticator.getUserInfo().type())
            {
                case INVALID -> invalidLoginAttempt();
                case END_USER -> loginAsEndUser();
                case EVENT_COORDINATOR -> loginAsCoordinator();
                case ADMINISTRATOR -> loginAsAdmin();
            }
        }
    }

    private void loginAsEndUser()
    {

    }

    private void loginAsCoordinator()
    {
        Parent root = null;

        try
        {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/coordinatorView.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.borderPaneRoot.getScene().setRoot(root);
    }

    private void loginAsAdmin()
    {

    }

    private void invalidLoginAttempt()
    {

    }
}

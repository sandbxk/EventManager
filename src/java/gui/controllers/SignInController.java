package gui.controllers;

import be.EUserType;
import be.Event;
import bll.DatabaseAuthenticator;
import bll.SessionManager;
import bll.interfaces.IAuthenticator;
import gui.model.SceneManager;
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
import java.util.Locale;
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
        SessionManager.login(usernameField.getText(), passwordField.getText());

        if (usernameField.getText().toLowerCase(Locale.ROOT).equals("admin"))
        {
            loginAsAdmin();
        }
        else if (usernameField.getText().toLowerCase(Locale.ROOT).equals("c") || (usernameField.getText().toLowerCase(Locale.ROOT).equals("coordinator")))
        {
            loginAsCoordinator();
        }

        switch (SessionManager.getCurrent().getLoggedInUser().type())
        {
            case INVALID -> invalidLoginAttempt();
            case END_USER -> loginAsEndUser();
            case EVENT_COORDINATOR -> loginAsCoordinator();
            case ADMINISTRATOR -> loginAsAdmin();
        }

    }

    private void loginAsEndUser()
    {
        SceneManager.DASHBOARD_USER.setAsCurrent();
    }

    private void loginAsCoordinator()
    {
        SceneManager.DASHBOARD_COORDINATOR.setAsCurrent();
    }

    private void loginAsAdmin()
    {
        SceneManager.DASHBOARD_ADMIN.setAsCurrent();
    }

    private void invalidLoginAttempt()
    {
        System.out.println("failed login");
        // failed login
    }
}

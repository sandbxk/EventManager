package gui.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public enum SceneManager
{
    LOGIN_FORM("MainView.fxml"),
    DASHBOARD_USER("exampleView.fxml"),
    DASHBOARD_COORDINATOR("coordinatorView.fxml"),
    DASHBOARD_ADMIN("adminView.fxml"),
    COORDINATOR_NEW_EVENT("NewEventView.fxml");

    private final String rootPath = "../views/";

    SceneManager(String file)
    {
        Parent tmp = null;
        try
        {
             tmp = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(rootPath + file)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.m_this_scene = tmp;
        }
    }

    public void setAsCurrent()
    {
        window.getScene().setRoot(m_this_scene);
    }

    private final Parent m_this_scene;

    private static Stage window;

    public static void init(Stage primary)
    {
        window = primary;
        window.setTitle("TicketGuru");
        window.setScene(new Scene(SceneManager.LOGIN_FORM.m_this_scene));
        window.show();
    }

}

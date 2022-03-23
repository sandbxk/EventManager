package gui.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum SceneManager
{
    LOGIN_FORM("MainView.fxml"),
    DASHBOARD_USER("exampleView.fxml"),
    DASHBOARD_COORDINATOR("coordinatorView.fxml"),
    DASHBOARD_ADMIN("adminView.fxml");

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

    private static final Stage window = new Stage();

    public static void init(Stage primary) throws IOException
    {
        mapping.put(SceneManager.LOGIN_FORM, FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("../views/MainView.fxml"))));
        mapping.put(SceneManager.DASHBOARD_USER, FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("../views/exampleView.fxml"))));
        mapping.put(SceneManager.DASHBOARD_COORDINATOR, FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("../views/coordinatorView.fxml"))));
        mapping.put(SceneManager.DASHBOARD_ADMIN, FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("../views/adminView.fxml"))));

        Scene scene = new Scene(mapping.get(SceneManager.LOGIN_FORM));

        window.setTitle("TicketGuru");
        window.setScene(scene);
        window.show();
    }

    public static void set(SceneManager e)
    {
        window.getScene().setRoot(mapping.get(e));
    }

    private static final Map<SceneManager, Parent> mapping = new HashMap<>();
}

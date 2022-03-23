import gui.model.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.Properties;

public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        SceneManager.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

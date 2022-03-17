package gui.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;


public class EventEntity {

    private Node view;
    private EventEntityController controller;


    //Type cast fxml to Node and use Event as controller?
    public EventEntity() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view\\CourseObject.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new EventEntityController());
        try {
            view = fxmlLoader.load();
        } catch (IOException ignored) {}
        getChildren().add(view);
    }
}

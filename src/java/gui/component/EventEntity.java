package gui.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class EventEntity {

    private Node node;

    public @FXML ImageView imgViewbanner;
    public @FXML Label lblEventName;
    public @FXML Label lblDateTime;


    //Type cast fxml to Node and use Event as controller?
    public EventEntity() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/views/EventEntity.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try { node = fxmlLoader.load();
        } catch (IOException ignored) {}
    }
}

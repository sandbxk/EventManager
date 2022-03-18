package be;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.w3c.dom.css.RGBColor;

import java.io.IOException;
import java.time.LocalDateTime;

public class Event {

    private int id;
    private StringProperty eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ObjectProperty location;
    private int ticketsSold;
    private int ticketsRemaining;
    private double[] ticketPrice;

    private ToggleButton node;

    public @FXML ImageView imgViewbanner;
    public @FXML Label lblEventName;
    public @FXML Label lblEventDateTime;


    //Type cast fxml to Node and use Event as controller?
    public Event() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/EventNode.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try { node = fxmlLoader.load();
        } catch (IOException ignored) {}
    }

    /**
     * Generates an image of a single color, with a 1x1 pixel resolution, which can then be scaled to the size of the ImageView.
     * @return a blank image.
     */
    private Image generateBlankImage(Color color) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();
        pw.setColor(0, 0, color);
        return img ;
    }

    public ToggleButton getEventTile(){
        return node;
    }


}

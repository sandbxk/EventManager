package be;

import bll.DataManager;
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
import javafx.scene.shape.Rectangle;
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

    public @FXML ImageView imgViewBanner;
    public @FXML Label lblEventName;
    public @FXML Label lblEventDateTime;
    public @FXML GridPane imgViewContainer;



    public Event() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/Event.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try { node = fxmlLoader.load();
            node.getStylesheets().add(getClass().getResource("/gui/styles/event.css").toExternalForm());
        } catch (IOException ignored) {}
        node.setOnAction(event -> DataManager.getInstance().setSelectedEvent(this));

        Rectangle clip = new Rectangle(imgViewBanner.getFitWidth(), imgViewBanner.getFitHeight());
        clip.setArcHeight(9);
        clip.setArcWidth(9);
        clip.setStroke(Color.TRANSPARENT);
        imgViewBanner.setClip(clip);


        Image image = generateBlankImage(Color.AQUAMARINE);

        imgViewBanner.setPreserveRatio(false);
        imgViewBanner.setImage(image);

    }

    public Event(int id, StringProperty eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, ObjectProperty venue, int ticketsSold, int ticketsRemaining, double[] ticketPrice) {
        this.id = id;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = venue;
        this.ticketsSold = ticketsSold;
        this.ticketsRemaining = ticketsRemaining;
        this.ticketPrice = ticketPrice;


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/Event.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try { node = fxmlLoader.load();
            node.getStylesheets().add(getClass().getResource("/gui/styles/event.css").toExternalForm());
        } catch (IOException ignored) {}
        node.setOnAction(event -> DataManager.getInstance().setSelectedEvent(this));
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

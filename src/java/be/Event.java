package be;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

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

    private VBox node;
    private Image banner;

    public Event() {
    }

    private void createNode(){
        node = new VBox();
        node.setMaxSize(450, 150);
        node.setStyle("-fx-background-color: orange");
        

    }


}

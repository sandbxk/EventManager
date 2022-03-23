package be;

import bll.DataManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {

    private int id;
    private StringProperty eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ObjectProperty<Venue> location;
    private int ticketsSold;
    private int ticketsRemaining;
    private List<PriceGroup> priceGroupList;
    private String description;
    private Image eventImage;

    private Color color;

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

    /**
     * Creates an event with a solid user defined color for the image.
     * @param id
     * @param eventName
     * @param startDateTime
     * @param endDateTime
     * @param venue
     * @param ticketsSold
     * @param ticketsRemaining
     * @param priceGroupList
     * @param description
     * @param color
     */
    public Event(int id, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, Venue venue, int ticketsSold, int ticketsRemaining, List<PriceGroup> priceGroupList, String description, Color color) {

        this.id = id;
        this.eventName = new SimpleStringProperty();
        this.eventName.set(eventName);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = new SimpleObjectProperty<>();
        this.location.set(venue);

        this.ticketsSold = ticketsSold;
        this.ticketsRemaining = ticketsRemaining;
        this.priceGroupList = priceGroupList;
        this.description = description;
        this.color = color;
        this.eventImage = generateBlankImage(this.color);


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
        imgViewBanner.setPreserveRatio(false);
        imgViewBanner.setImage(eventImage);
        lblEventName.setText(eventName);
        lblEventDateTime.setText(startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + (startDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME)));
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


    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName.get();
    }

    public StringProperty eventNameProperty() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName.set(eventName);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Venue getLocation() {
        return location.get();
    }

    public ObjectProperty locationProperty() {
        return location;
    }

    public void setLocation(Venue location) {
        this.location.set(location);
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public int getTicketsRemaining() {
        return ticketsRemaining;
    }

    public void setTicketsRemaining(int ticketsRemaining) {
        this.ticketsRemaining = ticketsRemaining;
    }

    public List<PriceGroup> getPriceGroups() {
        return priceGroupList;
    }

    public void setTicketPrice(List<PriceGroup> priceGroupList) {
        this.priceGroupList = priceGroupList;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getEventImage(){
        return eventImage;
    }

    public void setEventImage(Image eventImage){
        this.eventImage = eventImage;
    }

    public Color getColor(){
        return this.color;
    }

}

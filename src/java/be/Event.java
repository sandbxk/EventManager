package be;

import bll.DataManager;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
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

public class Event {

    private int id;
    private StringProperty eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ObjectProperty<Venue> location;
    private int ticketsSold;
    private int ticketsRemaining;
    private ListProperty<PriceGroup> priceGroupsList;
    private String description;
    private Image eventImage;
    private Color color;
    private boolean hasImage;


    //TODO: List of categories
    //TODO: List of attendees


    private ToggleButton node;

    public @FXML ImageView imgViewBanner;
    public @FXML Label lblEventName;
    public @FXML Label lblEventDateTime;
    public @FXML GridPane imgViewContainer;



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
    public Event(int id, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, Venue venue, int ticketsSold, int ticketsRemaining, ObservableList<PriceGroup> priceGroupList, String description, Color color, Image image) {

        this.id = id;
        this.eventName = new SimpleStringProperty();
        this.eventName.set(eventName);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = new SimpleObjectProperty<>();
        this.location.set(venue);

        this.ticketsSold = ticketsSold;
        this.ticketsRemaining = ticketsRemaining;
        this.priceGroupsList = new SimpleListProperty<>();
        this.priceGroupsList.set(priceGroupList);
        this.description = description;

        initImageAndColor(image, color);

        //NODE representation
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/Event.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try { node = fxmlLoader.load();
        } catch (IOException ignored) {}
        node.setOnAction(event -> DataManager.getInstance().setSelectedEvent(this));

        //Event Node init
        imgViewContainer.setStyle("-fx-background-color: rgb(" + color.getRed()*255 + ", " + color.getGreen()*255 + ", " + color.getBlue()*255 + ");");
        Rectangle clip = new Rectangle(imgViewBanner.getFitWidth(), imgViewBanner.getFitHeight());
        clip.setArcHeight(9);
        clip.setArcWidth(9);
        clip.setStroke(Color.TRANSPARENT);
        imgViewBanner.setClip(clip);
        imgViewBanner.setPreserveRatio(false);
        imgViewBanner.setImage(eventImage);
        lblEventName.setText(eventName);
        lblEventDateTime.setText(startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    /**
     * Sets the image and color of the event.
     * If the image is null, the eventImage is set to the color of the event.
     * @param image
     * @param color
     */
    private void initImageAndColor(Image image, Color color){
        if (image == null){
            this.color = color;
            this.eventImage = generateBlankImage(color);
            this.hasImage = false;
        }
        else {
            this.color = Color.TRANSPARENT;
            this.eventImage = image;
            this.hasImage = true;
        }
    }

    /**
     * The node representation of the event object.
     * @return
     */
    public ToggleButton getEventTile(){
        return node;
    }

    /**
     * Generates a static image of the given color, which can then be scaled to the imageview.
     * @param color
     * @return
     */
    private Image generateBlankImage(Color color) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();
        pw.setColor(0, 0, color);
        return img ;
    }


    /* Getters and Setters */

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
        if (eventName == null || eventName.isEmpty() || eventName.isBlank())
            eventName = "Unamed";

        this.eventName.set(eventName);
        this.lblEventName.setText(eventName);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.lblEventDateTime.setText(startDateTime.toLocalTime().toString());
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

    public ObservableList<PriceGroup> getPriceGroups() {
        return this.priceGroupsList.get();
    }

    public ListProperty<PriceGroup> getPriceGroupsProperty(){
        return this.priceGroupsList;
    }

    public void setPriceGroups(ObservableList<PriceGroup> list) {
        this.priceGroupsList.set(list);
    }

    public void addPriceGroup(PriceGroup priceGroup){
        this.priceGroupsList.get().add(priceGroup);
    }

    public void removePriceGroup(PriceGroup priceGroup){
        this.priceGroupsList.get().remove(priceGroup);
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
        this.imgViewBanner.setImage(eventImage);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.imgViewContainer.setStyle("-fx-background-color: rgb(" + color.getRed()*255 + ", " + color.getGreen()*255 + ", " + color.getBlue()*255 + ");");
    }

    public boolean HasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}

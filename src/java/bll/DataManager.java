package bll;

import be.Event;
import be.PriceGroup;
import be.Venue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public final class DataManager {


    private static DataManager instance;

    private Venue selectedVenue;

    private PriceGroup selectedPriceGroup;

    /**
     * The selected event
     */
    private ObjectProperty<Event> selectedEvent;

    /**
     * List of all valid events. User dependent.
     */
    private ObservableList<Event> events;

    /**
     * List of all Venues. Not user dependent.
     */
    private ObservableList<Venue> allVenues;

    /**
     * List of all priceGroups for a given event. Use is temporary -->
     *      Used for creating a new event. This stores the priceGroups of an event that is about to be initialized.
     *      Used for editing an event. The PriceGroups of an event are temporarily stored here and edited until the
     *          edit is saved.
     */
    private ObservableList<PriceGroup> priceGroups;


    public DataManager() {
        this.instance = this;
        this.selectedEvent = new SimpleObjectProperty<>();

        //Avoid nullPointers
        this.events = FXCollections.observableArrayList();
        this.allVenues = FXCollections.observableArrayList();
        this.priceGroups = FXCollections.observableArrayList();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * EVENTS
     */

    public void newEvent(Event event){
        //TODO: DB
        this.events.add(event);
    }

    public void deleteEvent(Event event){
        //TODO: DB
    }

    public void updateEvent(Event event){
        //TODO: DB
    }

    public ObservableList<Event> getAllEvents() {
        //TODO: DB
        return this.events;
    }

    public void setEvents(ObservableList<Event> events) {
        this.events = events;
    }

    public ObjectProperty<Event> getSelectedEventProperty(){
        return selectedEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent.get();
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent.set(selectedEvent);
    }


    /** PRICE GROUPS */

     /** If there is not a specific event to retrive the price groups for, it returns the volatile PriceGroups List in
      * DataManager. Used for an event that is about to be created.
     * @param event
     * @return
     */
    public ObservableList<PriceGroup> getPriceGroups(Event event){
        if (event == null) {
            return this.priceGroups;
        }
        else return event.getPriceGroups();
    }

    /**
     * Creates a new price group for an event. If the event is about to be created, e.g. in NewEventView.fxml,
     * it adds the PriceGroup the volatile PriceGroupsList in DataManager.
     * @param event
     * @param priceGroup
     */
    public void newPriceGroup(Event event, PriceGroup priceGroup) {

        if (event == null) {
            this.priceGroups.add(priceGroup);
        }
        else event.getPriceGroups().add(priceGroup);
    }

    public void removePriceGroup(Event event, PriceGroup priceGroup){
        if (event == null) {
            this.priceGroups.remove(priceGroup);
        }
        else event.getPriceGroups().remove(priceGroup);
    }

    /**
     * Sets the this.PriceGroups. Used as a reset for working with an event in creation, or an existing event.
     * @param priceGroups
     */
    public void setPriceGroups(ObservableList<PriceGroup> priceGroups){
        this.priceGroups = priceGroups;
    }

    public void updatePriceGroup(PriceGroup selectedPriceGroup) {
        //TODO: DB
    }

    public PriceGroup getSelectedPriceGroup() {
        return this.selectedPriceGroup;
    }

    public void setSelectedPriceGroup(PriceGroup priceGroup) {
        this.selectedPriceGroup = priceGroup;
    }


    /** VENUE */

    public ObservableList<Venue> getAllVenues(){
        //TODO: DB
        return this.allVenues;
    }

    public void newVenue(Venue venue) {

        //TODO: TEMP
        this.allVenues.add(venue);
    }

    public void updateVenue(Venue venue) {
        //TODO: DB
    }

    public void removeVenue(Venue selectedItem) {
        this.priceGroups.remove(selectedItem);
        //TODO: DB
    }

    public Venue getSelectedVenue() {
        return this.selectedVenue;
    }

    public void setSelectedVenue(Venue venue) {
        this.selectedVenue = venue;
    }

    public void createNewUser()
    {
        
    }
}


package bll;

import be.Event;
import be.PriceGroup;
import be.Venue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class DataManager {


    private static DataManager instance;

    private Venue selectedVenue;

    private PriceGroup selectedPriceGroup;

    private ObjectProperty<Event> selectedEvent;

    private ObservableList<Event> events;

    private ObservableList<Venue> allVenues;

    private ObservableList<PriceGroup> priceGroups;


    public DataManager() {
        this.instance = this;
        this.selectedEvent = new SimpleObjectProperty<>();

        //TEMP
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

    public ObjectProperty<Event> getSelectedEventProperty(){
        return selectedEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent.get();
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent.set(selectedEvent);
    }

    public ObservableList<Venue> getAllVenues(){
        return FXCollections.observableArrayList();
    }

    public ObservableList<PriceGroup> getPriceGroups(Event event){

        if (event == null) {
            return this.priceGroups;
        }
        else return event.getPriceGroups();
    }

    public PriceGroup getSelectedPriceGroup() {
        return this.selectedPriceGroup;
    }

    public void setSelectedPriceGroup(PriceGroup priceGroup) {
        this.selectedPriceGroup = priceGroup;
    }

    public Venue getSelectVenue() {
        return this.selectedVenue;
    }

    public void setSelectedVenue(Venue venue) {
        this.selectedVenue = venue;
    }

    public void newVenue(Venue venue) {

        //TODO: TEMP
        this.allVenues.add(venue);
    }

    public void updateVenue(Venue venue) {

    }

    /**
     * Only for edit Event. Creates a new price group for an existing event.
     * @param event
     * @param priceGroup
     */
    public void newPriceGroup(Event event, PriceGroup priceGroup) {


        if (event == null) {
            this.priceGroups.add(priceGroup);
        }
    }

    public void setPriceGroups(ObservableList<PriceGroup> priceGroups){
        this.priceGroups = priceGroups;
    }

    public void updatePriceGroup(PriceGroup selectedPriceGroup) {

    }

    public void removeVenue(Venue selectedItem) {
    }
}

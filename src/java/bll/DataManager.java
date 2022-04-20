package bll;

import be.Event;
import be.PriceGroup;
import be.UserInfo;
import be.Venue;
import dal.DAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DataManager {

    private DAO database;

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


    public DataManager()
    {
        this.instance = this;
        this.selectedEvent = new SimpleObjectProperty<>();

        //Avoid nullPointers
        this.events = FXCollections.observableArrayList();
        this.allVenues = FXCollections.observableArrayList();
        this.priceGroups = FXCollections.observableArrayList();

        database = new DAO();
    }

    public static DataManager getInstance()
    {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * EVENTS
     */

    public void newEvent(Event event)
    {
        String colour = event.getColor().getRed() + ", " + event.getColor().getGreen() + ", " + event.getColor().getBlue();

        int eventid = database.createEvent(event, colour);
        database.createPriceGroup(event.getPriceGroups(), eventid);
    }

    public void deleteEvent(Event event)
    {
        database.deleteAllUsersFromEvent(event.getId());
        database.deleteAllPrices(event.getId());
        database.deleteEvent(event.getId());
    }

    public void updateEvent(Event event)
    {
        String colour = event.getColor().getRed() + ", " + event.getColor().getGreen() + ", " + event.getColor().getBlue();

        database.updateEvent(event, colour);
    }

    public ObservableList<Event> getAllEvents() throws SQLException
    {
        return this.database.getAllEvents();
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
    public ObservableList<PriceGroup> getPriceGroupList(Event event){
        if (event == null) {
            return this.priceGroups;
        }
        else return database.getPriceGroup(event.getId());
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
        else database.createPrice(priceGroup.getName(), priceGroup.getPrice(), priceGroup.getCurrency(), event.getId());
    }

    public void removePriceGroup(Event event, PriceGroup priceGroup){
        if (event == null) {
            this.priceGroups.remove(priceGroup);
        }
        else database.deletePrice(priceGroup.getID());
    }

    /**
     * Sets the this.PriceGroups. Used as a reset for working with an event in creation, or an existing event.
     * @param priceGroups
     */
    public void setPriceGroupList(ObservableList<PriceGroup> priceGroups){
        this.priceGroups = priceGroups;
    }

    public void updatePriceGroup(PriceGroup selectedPriceGroup) {
        database.updatePrice(selectedPriceGroup.getName(), selectedPriceGroup.getPrice(), selectedPriceGroup.getCurrency(), selectedPriceGroup.getID());
    }

    public PriceGroup getSelectedPriceGroup() {
        return this.selectedPriceGroup;
    }

    public void setSelectedPriceGroup(PriceGroup priceGroup) {
        this.selectedPriceGroup = priceGroup;
    }


    /** VENUE */

    public ObservableList<Venue> getAllVenues() throws SQLException
    {
            this.allVenues = database.getAllVenues();
            return database.getAllVenues();

    }

    public void newVenue(Venue venue) {
        database.createVenue(venue.getVenueName(), venue.getAddress(), Integer.parseInt(venue.getZipCode()));
    }

    public void updateVenue(Venue venue) {
        database.updateVenue(venue.getVenueName(), venue.getAddress(), venue.getZipCode(), venue.getID());
    }

    public void removeVenue(Venue selectedItem) {
        database.deleteVenue(selectedItem.getID());
    }

    public Venue getSelectedVenue() {
        return this.selectedVenue;
    }

    public void setSelectedVenue(Venue venue) {
        this.selectedVenue = venue;
    }

    public void addUserToEvent(TableView<UserInfo> tableView, Event event)
    {
        List<UserInfo> userList = tableView.getItems();
         database.addUserToEvent(userList, event.getId());
    }

    public ObservableList<UserInfo> getAllUsers()
    {
        return database.getAllUsers();
    }

    public void createNewUser(String userName, String login, String password, String email)
    {
        database.createUser(userName, login, password, email);
    }

    public void removeUserFromEvent(UserInfo user, Event event)
    {
        database.removeUserFromEvent(user.getId(), event.getId());
    }

    public ObservableList<UserInfo> getUsersForEvent(Event event)
    {
        return database.getUsersForEvent(event.getId());
    }
}


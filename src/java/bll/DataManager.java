package bll;

import be.Event;
import be.PriceGroup;
import be.Venue;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class DataManager {

    private static DataManager instance;

    private ObjectProperty<Event> selectedEvent;

    public DataManager() {
        this.instance = this;
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
        return FXCollections.observableArrayList();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
}

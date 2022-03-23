package bll;

import be.Event;
import javafx.beans.property.ObjectProperty;

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

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
}

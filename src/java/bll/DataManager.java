package bll;

import be.Event;

public final class DataManager {

    private static DataManager instance;

    private Event selectedEvent;

    public DataManager() {
        this.instance = this;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
}

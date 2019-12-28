package com.mydarasa.app.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventListModel {

    @SerializedName("data")
    @Expose
    private EventsModel[] eventsModel;

    public EventsModel[] getEventsModel() {
        return eventsModel;
    }

    public void setEventsModel(EventsModel[] eventsModel) {
        this.eventsModel = eventsModel;
    }
}

package com.mydarasa.app.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RsvpModel {
    @SerializedName("eventNo")
    @Expose
    private String eventId;

    @SerializedName("status")
    @Expose
    private String  status;

    public RsvpModel() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

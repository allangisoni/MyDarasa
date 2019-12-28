package com.mydarasa.app.timetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimetableItemModel {

    @SerializedName("name")
    @Expose
    private String itemName;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

package com.mydarasa.app.timetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimetableListModel {

    @SerializedName("data")
    @Expose
    private TimetableModel[] timetableModel;

    public TimetableModel[] getTimetableModel() {
        return timetableModel;
    }
    public void setTimetableModel(TimetableModel[] timetableModel) {
        this.timetableModel = timetableModel;
    }
}

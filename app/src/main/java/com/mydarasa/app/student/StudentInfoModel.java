package com.mydarasa.app.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.cocurricular.CocurricularModel;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.progressreports.ProgressModel;

public class StudentInfoModel {

    @SerializedName("eventData")
    @Expose
    private EventsModel[] eventsModels;

    @SerializedName("coCurricularData")
    @Expose
    private CocurricularModel[] cocurricularModels;

    @SerializedName("progressData")
    @Expose
    private ProgressModel[] progressModels;

    public CocurricularModel[] getCocurricularModels() {
        return cocurricularModels;
    }

    public void setCocurricularModels(CocurricularModel[] cocurricularModels) {
        this.cocurricularModels = cocurricularModels;
    }

    public EventsModel[] getEventsModels() {
        return eventsModels;
    }

    public void setEventsModels(EventsModel[] eventsModels) {
        this.eventsModels = eventsModels;
    }

    public ProgressModel[] getProgressModels() {
        return progressModels;
    }

    public void setProgressModels(ProgressModel[] progressModels) {
        this.progressModels = progressModels;
    }
}

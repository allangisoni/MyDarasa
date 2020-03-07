package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.events.EventsModel;
import com.mydarasa.app.progressreports.ProgressModel;


public class CocurricularItemList {


    @SerializedName("schoolTypeNo")
    @Expose
    private String schoolTypeNo;

    @SerializedName("coCurricularData")
    @Expose
    private CocurricularModel[] cocurricularModel;

    @SerializedName("training")
    @Expose
    private TrainingModelList[] trainingModelList;

    @SerializedName("progressReportData")
    @Expose
    private ProgressModel[] progressModelsList;

    @SerializedName("eventsData")
    @Expose
    private EventsModel[] eventsModel;

    @SerializedName("schoolsData")
    @Expose
    private SchoolData[] schoolData;


    public CocurricularModel[] getCocurricularModel() {
        return cocurricularModel;
    }

    public void setCocurricularModel(CocurricularModel[] cocurricularModel) {
        this.cocurricularModel = cocurricularModel;
    }

    public String getSchoolTypeNo() {
        return schoolTypeNo;
    }

    public void setSchoolTypeNo(String schoolTypeNo) {
        this.schoolTypeNo = schoolTypeNo;
    }

    public SchoolData[] getSchoolData() {
        return schoolData;
    }

    public void setSchoolData(SchoolData[] schoolData) {
        this.schoolData = schoolData;
    }

    public TrainingModelList[] getTimetableModel() {
        return trainingModelList;
    }

    public void setTimetableModel(TrainingModelList[] trainingModelList) {
        this.trainingModelList = trainingModelList;
    }

    public ProgressModel[] getProgressModels() {
        return progressModelsList;
    }

    public void setProgressModels(ProgressModel[] progressModelsList) {
        this.progressModelsList = progressModelsList;
    }

    public EventsModel[] getEventsModel() {
        return eventsModel;
    }
}

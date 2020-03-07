package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.timetable.TimetableItemModel;

public class TrainingModelList {


    @SerializedName("classNo")
    @Expose
    private String classNo;

    @SerializedName("student")
    @Expose
    private String studentName;

    @SerializedName("feeItem")
    @Expose
    private TrainingModel[] trainingModel;


    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public TrainingModel[] getTimetableItemModel() {
        return trainingModel;
    }

    public void setTimetableItemModel(TrainingModel[] trainingModel) {
        this.trainingModel = trainingModel;
    }

}

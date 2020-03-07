package com.mydarasa.app.timetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.cocurricular.TrainingModel;

public class TimetableModel {

    @SerializedName("classNo")
    @Expose
    private String classNo;

    @SerializedName("student")
    @Expose
    private String studentName;

    @SerializedName("feeItem")
    @Expose
    private TimetableItemModel[] timetableItemModel;


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

    public TimetableItemModel[] getTimetableItemModel() {
        return timetableItemModel;
    }

    public void setTimetableItemModel(TimetableItemModel[] timetableItemModel) {
        this.timetableItemModel = timetableItemModel;
    }


}

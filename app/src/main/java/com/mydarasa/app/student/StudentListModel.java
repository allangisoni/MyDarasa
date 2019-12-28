package com.mydarasa.app.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentListModel {

    @SerializedName("data")
    @Expose
    private StudentModel[] studentModel;

    public StudentModel[] getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel[] studentModel) {
        this.studentModel = studentModel;
    }
}

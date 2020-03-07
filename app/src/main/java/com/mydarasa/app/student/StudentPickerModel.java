package com.mydarasa.app.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentPickerModel {

    @SerializedName("name")
    @Expose
    private String studentName;

    @SerializedName("id")
    @Expose
    private String studentId;

    public  StudentPickerModel(){

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

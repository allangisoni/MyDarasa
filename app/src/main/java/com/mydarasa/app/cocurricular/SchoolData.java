package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolData {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("studentNo")
    @Expose
    private String studentNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
}

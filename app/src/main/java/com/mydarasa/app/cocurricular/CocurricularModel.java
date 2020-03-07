package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CocurricularModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("regNo")
    @Expose
    private String registrationNo;

    @SerializedName("schoolTypeNo")
    @Expose
    private String schoolTypeNo;

    @SerializedName("studentNo")
    @Expose
    private String studentNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getSchoolTypeNo() {
        return schoolTypeNo;
    }

    public void setSchoolTypeNo(String schoolTypeNo) {
        this.schoolTypeNo = schoolTypeNo;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
}

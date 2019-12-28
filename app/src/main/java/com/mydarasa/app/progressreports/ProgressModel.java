package com.mydarasa.app.progressreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressModel {


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("student")
    @Expose
    private String studentName;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("description")
    @Expose
    private String reportDescription;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
}

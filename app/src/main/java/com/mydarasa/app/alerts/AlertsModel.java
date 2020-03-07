package com.mydarasa.app.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.chat.ChatModel;

public class AlertsModel {



    @SerializedName("alertTitle")
    @Expose
    private String alertTitle;

    @SerializedName("alertDescription")
    @Expose
    private String description;

    @SerializedName("alertCode")
    @Expose
    private String alertCode;

    @SerializedName("type")
    @Expose
    private String alertType;

    @SerializedName("alertDate")
    @Expose
    private String alertDate;

    @SerializedName("school")
    @Expose
    private String school;

    @SerializedName("className")
    @Expose
    private String className;

    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("studentRegNo")
    @Expose
    private String studentRegistration;

    @SerializedName("alertNo")
    @Expose
    private String alertNo;

    @SerializedName("chatItem")
    @Expose
    private ChatModel[] chatModel;


    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlertCode() {
        return alertCode;
    }

    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRegistration() {
        return studentRegistration;
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration = studentRegistration;
    }

    public ChatModel[] getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel[] chatModel) {
        this.chatModel = chatModel;
    }

    public String getAlertNo() {
        return alertNo;
    }

    public void setAlertNo(String alertNo) {
        this.alertNo = alertNo;
    }
}

package com.mydarasa.app.progressreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.chat.ChatModel;

public class ProgressModel {

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("student")
    @Expose
    private String studentName;


    @SerializedName("overall")
    @Expose
    private String reportDescription;


    @SerializedName("school")
    @Expose
    private String school;

    @SerializedName("classAdvisor")
    @Expose
    private String classAdvisor;

    @SerializedName("gradingPeriod")
    @Expose
    private String gradingPeriod;

    @SerializedName("schoolYear")
    @Expose
    private String schoolYear;

    @SerializedName("progressReportType")
    @Expose
    private String progressReportType;

    @SerializedName("progressReportItem")
    @Expose
    private ProgressReportItem[] progressReportItems;

    @SerializedName("chatItem")
    @Expose
    private ChatModel[] chatModel;

    @SerializedName("progressReportNo")
    @Expose
    private String progressReportNo;


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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassAdvisor() {
        return classAdvisor;
    }

    public void setClassAdvisor(String classAdvisor) {
        this.classAdvisor = classAdvisor;
    }

    public String getGradingPeriod() {
        return gradingPeriod;
    }

    public void setGradingPeriod(String gradingPeriod) {
        this.gradingPeriod = gradingPeriod;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getProgressReportType() {
        return progressReportType;
    }

    public void setProgressReportType(String progressReportType) {
        this.progressReportType = progressReportType;
    }

    public ProgressReportItem[] getProgressReportItems() {
        return progressReportItems;
    }

    public void setProgressReportItems(ProgressReportItem[] progressReportItems) {
        this.progressReportItems = progressReportItems;
    }

    public ChatModel[] getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel[] chatModel) {
        this.chatModel = chatModel;
    }

    public String getProgressReportNo() {
        return progressReportNo;
    }

    public void setProgressReportNo(String progressReportNo) {
        this.progressReportNo = progressReportNo;
    }
}

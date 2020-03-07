package com.mydarasa.app.progressreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressReportItem {

 @SerializedName("subject")
 @Expose
 private String subject;

 @SerializedName("score")
 @Expose
 private String score;

 @SerializedName("grade")
 @Expose
 private String grade;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

package com.mydarasa.app.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAlertChatItem {

    @SerializedName("alertNo")
    @Expose
    private String alertNo;

    @SerializedName("comments")
    @Expose
    private String comments;

    public String getAlertNo() {
        return alertNo;
    }

    public void setAlertNo(String alertNo) {
        this.alertNo = alertNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

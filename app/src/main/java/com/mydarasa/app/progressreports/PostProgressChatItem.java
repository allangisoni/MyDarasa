package com.mydarasa.app.progressreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProgressChatItem {

    @SerializedName("progressNo")
    @Expose
    private String progressNo;

    @SerializedName("comments")
    @Expose
    private String comments;

    public String getProgressNo() {
        return progressNo;
    }

    public void setProgressNo(String progressNo) {
        this.progressNo = progressNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

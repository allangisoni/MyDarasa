package com.mydarasa.app.progressreports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressRListModel {

    @SerializedName("data")
    @Expose
    private ProgressModel[] progressModels;

    public ProgressModel[] getProgressModels() {
        return progressModels;
    }

    public void setProgressModels(ProgressModel[] progressModels) {
        this.progressModels = progressModels;
    }
}

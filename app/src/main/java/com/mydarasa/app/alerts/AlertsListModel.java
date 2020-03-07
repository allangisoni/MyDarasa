package com.mydarasa.app.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.events.EventsModel;

public class AlertsListModel {
    @SerializedName("data")
    @Expose
    private AlertsModel[] alertsModels;

    public AlertsModel[] getAlertsModels() {
        return alertsModels;
    }

    public void setAlertsModels(AlertsModel[] alertsModels) {
        this.alertsModels = alertsModels;
    }
}

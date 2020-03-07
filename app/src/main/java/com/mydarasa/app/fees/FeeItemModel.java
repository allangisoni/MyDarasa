package com.mydarasa.app.fees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeItemModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("coast")
    @Expose
    private String cost;

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

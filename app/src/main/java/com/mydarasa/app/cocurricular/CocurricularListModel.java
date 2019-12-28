package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CocurricularListModel {

    @SerializedName("data")
    @Expose
    private CocurricularModel[] cocurricularModel;

    public CocurricularModel[] getCocurricularModel() {
        return cocurricularModel;
    }

    public void setCocurricularModel(CocurricularModel[] cocurricularModel) {
        this.cocurricularModel = cocurricularModel;
    }
}

package com.mydarasa.app.cocurricular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CocurricularListModel {

    @SerializedName("data")
    @Expose
    private CocurricularItemList[] cocurricularItemList;

    public CocurricularItemList[]  getCocurricularItemList() {
        return cocurricularItemList;
    }

    public void setCocurricularItemList(CocurricularItemList[] cocurricularItemList) {
        this.cocurricularItemList = cocurricularItemList;
    }
}

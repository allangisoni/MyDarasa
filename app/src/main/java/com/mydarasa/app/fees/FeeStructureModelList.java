package com.mydarasa.app.fees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStructureModelList {

    @SerializedName("data")
    @Expose
    private FeeStructureModel[] feeStructureModels;

    public FeeStructureModel[] getFeeStructureModels() {
        return feeStructureModels;
    }

    public void setFeeStructureModels(FeeStructureModel[] feeStructureModels) {
        this.feeStructureModels = feeStructureModels;
    }
}

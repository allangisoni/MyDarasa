package com.mydarasa.app.fees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStatementDetailsListModel {

    @SerializedName("data")
    @Expose
    private FeeStatementDetailsModel[] feeStatementDetailsModels;

    public FeeStatementDetailsModel[] getFeeStatementDetailsModels() {
        return feeStatementDetailsModels;
    }

    public void setFeeStatementDetailsModels(FeeStatementDetailsModel[] feeStatementDetailsModels) {
        this.feeStatementDetailsModels = feeStatementDetailsModels;
    }
}

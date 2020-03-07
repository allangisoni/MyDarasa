package com.mydarasa.app.fees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeeStatementModel  {

    @SerializedName("feeAmount")
    @Expose
    private String feeAmount;

    @SerializedName("feeBalance")
    @Expose
    private String feeBal;

    @SerializedName("studentName")
    @Expose
    private String studentName;

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeBal() {
        return feeBal;
    }

    public void setFeeBal(String feeBal) {
        this.feeBal = feeBal;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}

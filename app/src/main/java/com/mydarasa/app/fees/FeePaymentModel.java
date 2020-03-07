package com.mydarasa.app.fees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeePaymentModel {

    @SerializedName("amount")
    @Expose
    private  String amount;

    @SerializedName("paymentType")
    @Expose
    private  String paymentType;

    @SerializedName("studentNo")
    @Expose
    private  String studentNo;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
}

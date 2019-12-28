package com.mydarasa.app.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("message")
    private String message = " ";
    @SerializedName("status")
    private String status = " ";

    public LoginResponseModel(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

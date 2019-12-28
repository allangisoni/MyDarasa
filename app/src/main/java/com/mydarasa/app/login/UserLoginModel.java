package com.mydarasa.app.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserLoginModel {

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("message")
    private String message = " ";

    @SerializedName("status")
    private String status = " ";

    @SerializedName("guardianNo")
    @Expose
    private String guardianNo;

    @SerializedName("token")
    private TokenDetails tokenDetails;

public UserLoginModel (String password, String userName){
    this.userName = userName;
    this.password = password;
}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public TokenDetails getTokenDetails() {
        return tokenDetails;
    }

    public void setTokenDetails(TokenDetails tokenDetails) {
        this.tokenDetails = tokenDetails;
    }

    public String getGuardianNo() {
        return guardianNo;
    }

    public void setGuardianNo(String guardianNo) {
        this.guardianNo = guardianNo;
    }
}

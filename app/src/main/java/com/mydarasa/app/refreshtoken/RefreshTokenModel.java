package com.mydarasa.app.refreshtoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydarasa.app.login.TokenDetails;

public class RefreshTokenModel {

    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;


    @SerializedName("token")
    @Expose
    private TokenDetails tokenDetails;

    public RefreshTokenModel(String refreshToken){
        this.refreshToken = refreshToken;
    }



    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public TokenDetails getTokenDetails() {
        return tokenDetails;
    }

    public void setTokenDetails(TokenDetails tokenDetails) {
        this.tokenDetails = tokenDetails;
    }
}

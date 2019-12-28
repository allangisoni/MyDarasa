package com.mydarasa.app.guardian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardianUser {

    @SerializedName("user")
    @Expose
    private GuardianModel user;

    @SerializedName("physicalAddress")
    @Expose
    private String physicalAddress;

    @SerializedName("postalAddress")
    @Expose
    private String postalAddress;

    public GuardianUser(){

    }


    public GuardianModel getUser() {
        return user;
    }

    public void setUser(GuardianModel user) {
        this.user = user;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {

    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
}

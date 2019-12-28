package com.mydarasa.app.guardian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardianData {

    @SerializedName("data")
    @Expose
    private GuardianUser guardianUser;

    public GuardianData(){

    }

    public GuardianUser getGuardianUser() {
        return guardianUser;
    }

    public void setGuardianUser(GuardianUser guardianUser) {
        this.guardianUser = guardianUser;
    }
}

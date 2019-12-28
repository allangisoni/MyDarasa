package com.mydarasa.app.guardian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuardianModel {



    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("phone")
    @Expose
    private String phoneNumber;


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("pin")
    @Expose
    private String pin;


    public  GuardianModel(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

package com.mydarasa.app.register;

import com.google.gson.annotations.SerializedName;

public class GuardianModel {

    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("otp")
    private String otp;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("physicalAddress")
    private String physicalAddress;

    @SerializedName("pin")
    private String pin;

    @SerializedName("postalAddress")
    private String postalAddress;


    public GuardianModel(String email,String firstName,String lastName, String otp, String phoneNumber, String physicalAddress,
                         String pin, String postalAddress ){

        this.email =email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otp = otp;
        this.phoneNumber = phoneNumber;
        this.physicalAddress = physicalAddress;
        this.pin = pin;
        this.postalAddress = postalAddress;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

}

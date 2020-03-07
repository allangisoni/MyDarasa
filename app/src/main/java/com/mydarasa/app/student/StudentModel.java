package com.mydarasa.app.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("middleName")
    @Expose
    private String middleName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("registration")
    @Expose
    private String registration;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("className")
    @Expose
    private String classId;

    @SerializedName("classes")
    @Expose
    private Classes classes;

    @SerializedName("streamId")
    @Expose
    private String streamId;

    @SerializedName("stream")
    @Expose
    private Stream stream;

    @SerializedName("schoolName")
    @Expose
    private String schoolId;

    @SerializedName("name")
    @Expose
    private String name;

    public StudentModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

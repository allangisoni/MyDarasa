package com.mydarasa.app.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsModel {

    @SerializedName("eventNo")
    @Expose()
    private String eventId;

    @SerializedName("evenName")
    @Expose
    private String eventName;

    @SerializedName("type")
    @Expose
    private String eventType;

    @SerializedName("school")
    @Expose
    private String school;

    @SerializedName("eventDate")
    @Expose
    private String eventDate;

    @SerializedName("className")
    @Expose
    private String className;

    @SerializedName("studentRegNo")
    @Expose
    private String studentRegistration;

    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("rsvp")
    @Expose
    private String rsvp;

    public EventsModel(){

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentRegistration() {
        return studentRegistration;
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration = studentRegistration;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRsvp() {
        return rsvp;
    }

    public void setRsvp(String rsvp) {
        this.rsvp = rsvp;
    }
}

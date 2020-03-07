package com.mydarasa.app.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel {

   // private String chatPerson;


   // private String chatMessage;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("alertNo")
    @Expose
    private String alertNo;

    @SerializedName("postedBy")
    @Expose
    private String chatPerson;

    @SerializedName("timeStamp")
    @Expose
    private String chatTime;

    public ChatModel(String chatPerson,String chatTime,String comment){
        this.chatPerson = chatPerson;
        this.chatTime =chatTime;
        this.comment = comment;
    }

    public  ChatModel(){

    }
    public String getChatPerson() {
        return chatPerson;
    }

    public void setChatPerson(String chatPerson) {
        this.chatPerson = chatPerson;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

   /** public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
**/
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAlertNo() {
        return alertNo;
    }

    public void setAlertNo(String alertNo) {
        this.alertNo = alertNo;
    }
}

package com.mmm.questions;

import com.google.firebase.auth.FirebaseAuth;

public class Post {
    public String userID;
    private String currentTime;
    private String currentDate;
    private String content;

    public Post(){
        this.userID = "";
        this.currentTime = "";
        this.currentDate = "";
        this.content = "";
    }

    public Post(String userID, String currentTime, String currentDate, String content){
        this.userID = userID;
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.content = content;
    }



    public String getUser(){
        return userID;
    }

    public String getContent(){
        return content;
    }

    public String getCurrentTime(){
        return currentTime;
    }

    public String getCurrentDate(){
        return currentDate;
    }
}

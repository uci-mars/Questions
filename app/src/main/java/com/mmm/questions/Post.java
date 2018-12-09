package com.mmm.questions;

public class Post {
    private String userID;
    private String currentTime;
    private String content;

    public Post(String userID, String currentTime, String content){
        this.userID = userID;
        this.currentTime = currentTime;
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
}

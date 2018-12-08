package com.mmm.questions;

public class User {

    private String name;
    private String email;
    private String password;

    public User(String n,String e, String p){
        email = e;
        password = p;
        name = n;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

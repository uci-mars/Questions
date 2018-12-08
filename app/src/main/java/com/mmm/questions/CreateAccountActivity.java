package com.mmm.questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//to create your account and add to firebase's authentication
public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Create Account");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }
}

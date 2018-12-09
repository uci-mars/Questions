package com.mmm.questions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);
    }

    public void goto_CreateAccount(View view){
        Intent intent = new Intent(this,com.mmm.questions.CreateAccountActivity.class);
        startActivity(intent);
    }

    public void goto_SignIn(View view){
        Intent intent = new Intent(this,com.mmm.questions.SignInActivity.class);
        startActivity(intent);
    }


}

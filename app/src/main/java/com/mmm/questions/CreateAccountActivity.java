package com.mmm.questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


//to create your account and add to firebase's authentication
public class CreateAccountActivity extends AppCompatActivity {

    private EditText userEmail, userPass, userConfirmPass, userName;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Create Account");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userEmail = (EditText)findViewById(R.id.emailText);
        userPass = (EditText)findViewById(R.id.passwordText);
        userConfirmPass = (EditText)findViewById(R.id.passwordConfirmText);
        userName = (EditText)findViewById(R.id.nameText);
        createAccountButton = (Button)findViewById(R.id.createButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
    }
}

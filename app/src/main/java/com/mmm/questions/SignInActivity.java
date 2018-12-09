package com.mmm.questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText userEmail, userPass;
    private Button signInButton;
    private TextView needNewAccount;

    //for loging in
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Sign In");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        myAuth = FirebaseAuth.getInstance();

        needNewAccount = (TextView)findViewById(R.id.needNewAccount);
        userEmail = (EditText)findViewById(R.id.emailText);
        userPass = (EditText)findViewById(R.id.passwordText);
        signInButton = (Button)findViewById(R.id.signInButton);

        //if the user clicks on the text for a new account then it sends them to the create account activity
        needNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, com.mmm.questions.CreateAccountActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

    }

    private void logIn() {
        String email = userEmail.getText().toString().trim();
        String pass = userPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be blank",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password cannot be blank",Toast.LENGTH_SHORT).show();
        }else{
            myAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Toast.makeText(SignInActivity.this, "", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, com.mmm.questions.MainActivity.class));

                    }else{
                        String m = task.getException().getMessage();
                        Toast.makeText(SignInActivity.this, "Error: " + m, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}

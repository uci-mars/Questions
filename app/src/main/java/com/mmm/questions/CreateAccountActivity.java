package com.mmm.questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


//to create your account and add to firebase's authentication
public class CreateAccountActivity extends AppCompatActivity {

    private EditText userEmail, userPass, userConfirmPass, userName;
    private Button createAccountButton;

    //firebase stuff
    private FirebaseAuth myAuth;
    private DatabaseReference databaseReference;    //used to push user account into to firebase
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Create Account");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //get firebase authentication info
        myAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");

        //getting the feilds
        userEmail = (EditText)findViewById(R.id.emailText);
        userPass = (EditText)findViewById(R.id.passwordText);
        userConfirmPass = (EditText)findViewById(R.id.passwordConfirmText);
        userName = (EditText)findViewById(R.id.nameText);
        createAccountButton = (Button)findViewById(R.id.createButton);

        //eventlistener for button just to make sure everything is inputted correctly
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        final String name = userName.getText().toString().trim();
        final String pass1 = userPass.getText().toString().trim();
        final String pass2 = userConfirmPass.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email cannot be left blank.", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please use a valid email.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Name cannot be left blank.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass1)){
            Toast.makeText(this, "Password cannot be left blank.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass2)){
            Toast.makeText(this, "Confirmation cannot be left blank.", Toast.LENGTH_SHORT).show();
        }else if(!pass1.equals(pass2)){
            Toast.makeText(this, "Your passwords do not match.", Toast.LENGTH_SHORT).show();
        }else{

            //if we pass all the fields: we push it to firebase
            myAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                //eventlisten
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(CreateAccountActivity.this, "Account created!", Toast.LENGTH_SHORT).show();

                        User u = new User(name,email,pass1);
                        databaseReference.child(databaseReference.push().getKey()).setValue(u);



                        Intent intent = new Intent(CreateAccountActivity.this,MainActivity.class);
                        startActivity(intent);

                        userEmail.setText("");
                        userName.setText("");
                        userPass.setText("");
                        userConfirmPass.setText("");

                    }else{
                        String m = task.getException().getMessage();
                        Toast.makeText(CreateAccountActivity.this, "Error: " + m, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}

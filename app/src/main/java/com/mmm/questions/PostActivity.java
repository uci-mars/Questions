package com.mmm.questions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private Button updatePostButton;
    private EditText postText;
    private String post;
    private DatabaseReference postReference;
    private FirebaseAuth userAuthentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("UpdatePost");
        setContentView(R.layout.activity_post);

        updatePostButton = (Button) findViewById(R.id.updatePostButton);
        postText = (EditText) findViewById(R.id.postText);
        postReference = FirebaseDatabase.getInstance().getReference("Posts");
        userAuthentication = FirebaseAuth.getInstance();

        updatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();
            }
        });

    }

    protected void OnDestroy()
    {
        super.onDestroy();
        userAuthentication.signOut();
        startActivity(new Intent(this, com.mmm.questions.SignInActivity.class));
    }

    public void ValidatePostInfo(){
        post = postText.getText().toString();
        if(TextUtils.isEmpty(post)){
            Toast.makeText(this, "Please input a question here...", Toast.LENGTH_SHORT).show();
        }

        else{
            StoreQuestionToFirebaseStorage(post);
        }

    }


    public void StoreQuestionToFirebaseStorage(String post){
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        String saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String saveCurrentTime = currentTime.format(calFordTime.getTime());


        String user_id = userAuthentication.getCurrentUser().getDisplayName();
        Post newPost = new Post(user_id, saveCurrentTime, saveCurrentDate, post);
        postReference.child(postReference.push().getKey()).setValue(newPost);
        Toast.makeText(this, newPost.getUser() + " successfully added a post!", Toast.LENGTH_LONG).show();
    }

}

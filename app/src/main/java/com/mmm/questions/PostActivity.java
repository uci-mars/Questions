package com.mmm.questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity {

    private Button updatePostButton;
    private EditText postText;
    private String post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("UpdatePost");
        setContentView(R.layout.activity_post);

        updatePostButton = (Button) findViewById(R.id.updatePostButton);
        postText = (EditText) findViewById(R.id.postText);

        updatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();
            }
        });

    }

    public void ValidatePostInfo(){
        post = postText.getText().toString();
        if(TextUtils.isEmpty(post)){
            Toast.makeText(this, "Please input a question here...", Toast.LENGTH_SHORT).show();
        }
        /*
        else{
            StoreQuestionToFirebaseStorage();
        }
        */
    }

    /*
    public void StoreQuestionToFirebaseStorage(){
        StorageReference filePath = postReference.child("Posts").child();
    }
    */
}

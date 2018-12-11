/*
Questions is made by:
Melissa Chin
Marwin Chheang
Michelle Wang

Github:
https://github.com/uci-mars/Questions
 */
package com.mmm.questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClickPostActivity extends AppCompatActivity {

    private TextView postView;
    private Button deleteButton;
    private String postKey;
    private DatabaseReference ClickPostRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);

        postView = (TextView) findViewById(R.id.postView);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        postKey = getIntent().getExtras().get("Post Key").toString();
        ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postKey);

        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String content = dataSnapshot.child("content").getValue().toString();
                    postView.setText(content);
                }
                catch (NullPointerException n){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DeleteCurrentPost();
                Toast.makeText(v.getContext(), "Post has been deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);


            }
        });
    }

    public void DeleteCurrentPost(){
        ClickPostRef.removeValue();
    }
}

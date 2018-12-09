package com.mmm.questions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAuth userAuthentication;

    private EditText searchText;
    private Button searchButton;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;
    private ArrayList<Post> contactList;
    private ArrayList<Post> searchResults;
    //private PostAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");

        searchButton = (Button)findViewById(R.id.searchButton);
        searchText = (EditText)findViewById(R.id.searchText);

        childEventListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                contactList.add(dataSnapshot.getValue(Post.class));
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}};


    }


    protected void OnDestroy()
    {
        super.onDestroy();
        userAuthentication.signOut();
        startActivity(new Intent(this, com.mmm.questions.HomeAcitivity.class));
    }
}

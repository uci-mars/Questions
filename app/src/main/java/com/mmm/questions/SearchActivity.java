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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    ;
    private ArrayList<Post> postList;
    private ArrayList<Post> searchResults;

    

    protected void OnDestroy()
    {
        super.onDestroy();
        userAuthentication.signOut();
        startActivity(new Intent(this, com.mmm.questions.HomeAcitivity.class));
    }
}

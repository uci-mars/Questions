package com.mmm.questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;

import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private Button searchButton;

    private RecyclerView questionsList;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference UsersRef;

    private ArrayList<Post> postList;
    private ArrayList<Post> searchResults;
    //private PostAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search");

        database = FirebaseDatabase.getInstance();
        UsersRef = database.getReference("Posts");

        searchButton = (Button)findViewById(R.id.searchButton);
        searchText = (EditText)findViewById(R.id.searchText);


        searchText = (EditText)findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Post");
        mAuth = FirebaseAuth.getInstance();

        questionsList = (RecyclerView) findViewById(R.id.questions_list);
        questionsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        questionsList.setLayoutManager(linearLayoutManager);

    }

    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(UsersRef, Post.class)
                        .build();

        FirebaseRecyclerAdapter<Post, MainActivity.PostViewHolder> adapter =
                new FirebaseRecyclerAdapter<Post, MainActivity.PostViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MainActivity.PostViewHolder holder, int position, @NonNull Post model) {
                        holder.userName.setText(model.getUser());
                        holder.questionPost.setText(model.getContent());
                        holder.dateStamp.setText(model.getCurrentDate());
                        holder.timeStamp.setText(model.getCurrentTime());
                    }

                    @NonNull
                    @Override
                    public MainActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout, parent, false);
                        MainActivity.PostViewHolder viewHolder = new MainActivity.PostViewHolder(view);
                        return viewHolder;
                    }
                };
        questionsList.setAdapter(adapter);

        adapter.startListening();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView userName, questionPost, dateStamp, timeStamp;
        public PostViewHolder(@NonNull View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.post_username);
            questionPost = itemView.findViewById(R.id.question);
            dateStamp = itemView.findViewById(R.id.date);
            timeStamp = itemView.findViewById(R.id.time);
        }
    }


    protected void OnDestroy()
    {
        super.onDestroy();
        mAuth.signOut();
        startActivity(new Intent(this, com.mmm.questions.SignInActivity.class));
    }
}

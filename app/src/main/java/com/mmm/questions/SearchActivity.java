package com.mmm.questions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private Button searchButton;
    private ArrayList<Post> postArr;

    private RecyclerView questionsList;
    private FirebaseRecyclerAdapter<Post, SearchActivity.PostViewHolder> adapter;
    private FirebaseRecyclerOptions<Post> option;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search");

        database = FirebaseDatabase.getInstance();

        searchButton = (Button)findViewById(R.id.searchButton);
        searchText = (EditText)findViewById(R.id.searchText);
        postArr = new ArrayList<Post>();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    search(editable.toString());
                }else{
                    search("");
                }
            }
        });

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mAuth = FirebaseAuth.getInstance();

        questionsList = (RecyclerView) findViewById(R.id.questions_list);
        questionsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        questionsList.setLayoutManager(linearLayoutManager);


        option = new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(UsersRef, Post.class)
                        .build();


        adapter =  new FirebaseRecyclerAdapter<Post, SearchActivity.PostViewHolder>(option) {
                    @Override
                    protected void onBindViewHolder(@NonNull SearchActivity.PostViewHolder holder, int position, @NonNull Post model) {
                        holder.userName.setText(model.userID);
                        holder.questionPost.setText(model.getContent());
                        holder.dateStamp.setText(model.getCurrentDate() + " @ ");
                        holder.timeStamp.setText(model.getCurrentTime());
                    }

                    @NonNull
                    @Override
                    public SearchActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout, parent, false);
                        SearchActivity.PostViewHolder viewHolder = new SearchActivity.PostViewHolder(view);
                        return viewHolder;
                    }
                };
        questionsList.setAdapter(adapter);

        adapter.startListening();

    }

    private void search(String s)
    {
        Query query = UsersRef.orderByChild("content")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    postArr.clear();
                    for(DataSnapshot s: dataSnapshot.getChildren()){
                        final Post item = s.getValue(Post.class);
                        postArr.add(item);
                    }
                    MyAdapter ma = new MyAdapter(getApplicationContext(),postArr);
                    questionsList.setAdapter(ma);
                    ma.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    protected void onStart()
//    {
//        super.onStart();

//        FirebaseRecyclerAdapter<Post, SearchActivity.PostViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Post, SearchActivity.PostViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull SearchActivity.PostViewHolder holder, int position, @NonNull Post model) {
//                        holder.userName.setText(model.userID);
//                        holder.questionPost.setText(model.getContent());
//                        holder.dateStamp.setText(model.getCurrentDate() + " @ ");
//                        holder.timeStamp.setText(model.getCurrentTime());
//                    }
//
//                    @NonNull
//                    @Override
//                    public SearchActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout, parent, false);
//                        SearchActivity.PostViewHolder viewHolder = new SearchActivity.PostViewHolder(view);
//                        return viewHolder;
//                    }
//                };
//        questionsList.setAdapter(adapter);
//
//        adapter.startListening();
//    }

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

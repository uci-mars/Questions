package com.mmm.questions;

// The "wall"

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;

import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView questionsList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private DatabaseReference PostRef;
    //private Button askButton;
    //for ser info
    private FirebaseAuth mAuth;

    private Button updatePostButton;
    private EditText postText;
    private String post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postText = (EditText) findViewById(R.id.question_text);

        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mAuth = FirebaseAuth.getInstance();

        updatePostButton = (Button) findViewById(R.id.raisehand_button);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ICS45J");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        questionsList = (RecyclerView) findViewById(R.id.questions_list);
        questionsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        questionsList.setLayoutManager(linearLayoutManager);


        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;

            }
        });

        updatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();
            }
        });


    }


    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(PostRef, Post.class)
                .build();

        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter =
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
                    private int position;
                    @Override
                    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
                        holder.userName.setText(model.userID);
                        holder.questionPost.setText(model.getContent());
                        holder.dateStamp.setText(model.getCurrentDate() + " @ ");
                        holder.timeStamp.setText(model.getCurrentTime());
                        this.position = position;
                    }

                    @NonNull
                    @Override
                    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout, parent, false);
                        PostViewHolder viewHolder = new PostViewHolder(view);
                        final String PostKey = getRef(position).getKey();

                        viewHolder.getView().setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                Intent clickPostIntent = new Intent(v.getContext(), ClickPostActivity.class);
                                clickPostIntent.putExtra("Post Key", PostKey);
                                startActivity(clickPostIntent);
                            }
                        });
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

        public View getView(){
            return itemView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
         }

        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector (MenuItem item){
        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(this, com.mmm.questions.MainActivity.class));
                break;

            case R.id.nav_search:
                startActivity(new Intent(this, com.mmm.questions.SearchActivity.class));
                break;

            case R.id.nav_logout:
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(this, com.mmm.questions.SignInActivity.class));
                break;
        }
    }

    protected void OnDestroy() {
        super.onDestroy();
        mAuth.signOut();
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


        String user_id = mAuth.getCurrentUser().getDisplayName();
        Post newPost = new Post(user_id, mAuth.getCurrentUser().getUid(), saveCurrentTime, saveCurrentDate, post);
        PostRef.child(PostRef.push().getKey()).setValue(newPost);
        Toast.makeText(this, newPost.getUser() + " successfully added a post!", Toast.LENGTH_LONG).show();
    }



    // uwu im just testing to see if i still know how to git
}

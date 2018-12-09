/*
Questions is made by:
Melissa Chin
Marwin Chheang
Michelle Wang

Github:
https://github.com/uci-mars/Questions
 */
package com.mmm.questions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>
{
    private Context context;
    private ArrayList<Post> arr;
    public MyAdapter(Context c, ArrayList<Post> a){
        context = c;
        arr = a;
    }

    public int getItemCount(){
        return arr.size();
    }
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_layout ,parent,false);
        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        Post model = arr.get(position);
        holder.userName.setText(model.userID);
        holder.questionPost.setText(model.getContent());
        holder.dateStamp.setText(model.getCurrentDate() + " @ ");
        holder.timeStamp.setText(model.getCurrentTime());
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView userName, questionPost, dateStamp, timeStamp;
        public MyAdapterViewHolder(View iV){
            super(iV);
            userName = itemView.findViewById(R.id.post_username);
            questionPost = itemView.findViewById(R.id.question);
            dateStamp = itemView.findViewById(R.id.date);
            timeStamp = itemView.findViewById(R.id.time);
        }
    }
}

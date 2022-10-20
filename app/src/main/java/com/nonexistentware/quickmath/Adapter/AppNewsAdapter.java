package com.nonexistentware.quickmath.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nonexistentware.quickmath.Model.AppNewsModel;
import com.nonexistentware.quickmath.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppNewsAdapter extends RecyclerView.Adapter<AppNewsAdapter.NewsViewHolder> {

    List<AppNewsModel> appNewsModelList;
    Context context;

    public AppNewsAdapter(List<AppNewsModel> appNewsModelList, Context context) {
        this.appNewsModelList = appNewsModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
       holder.newsTitle.setText(appNewsModelList.get(position).getNewsTitle());
       holder.newsBody.setText(appNewsModelList.get(position).getNewsBody());
       holder.newsDateTime.setText(appNewsModelList.get(position).getNewsDateTime());
       holder.newsImageTitle.setText(appNewsModelList.get(position).getNewsImage());
    }

    @Override
    public int getItemCount() {
        return appNewsModelList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle, newsBody, newsDateTime, newsImageTitle;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.news_item_title);
            newsBody = itemView.findViewById(R.id.news_item_body);
            newsImageTitle = itemView.findViewById(R.id.news_item_list_img);
            newsDateTime = itemView.findViewById(R.id.news_item_date_time);
        }
    }

}

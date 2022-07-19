package com.nonexistentware.quickmath.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nonexistentware.quickmath.Model.AppNewsModel;
import com.nonexistentware.quickmath.R;

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
       holder.newsTitle.setText("sgsdgdsg"+appNewsModelList.get(position).getNewsTitle());
       holder.newsBody.setText(appNewsModelList.get(position).getNewsBody());
    }

    @Override
    public int getItemCount() {
        return appNewsModelList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle, newsBody;
        ImageView newsImageTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.news_item_title);
            newsBody = itemView.findViewById(R.id.news_item_body);
            newsImageTitle = itemView.findViewById(R.id.news_item_list_img);
        }
    }

}

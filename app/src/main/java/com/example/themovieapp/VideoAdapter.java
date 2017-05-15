package com.example.themovieapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BLAQ on 5/14/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{


    private List<Video> VideoList;
    private static Context mContext;

    public VideoAdapter(Context mContext, List<Video> VideoList) {
        this.mContext=mContext;
        this.VideoList=VideoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);

        return new VideoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Video videoItem = VideoList.get(position);

        holder.Video_label.setText(videoItem.getLabel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /////lauch youtube video
                watchYoutubeVideo(videoItem.getKey());

            }
        });

    }

    @Override
    public int getItemCount() {
        return VideoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView Video_label;

        public MyViewHolder(View itemView) {
            super(itemView);

            Video_label = (TextView) itemView.findViewById(R.id.video_label);
        }
    }

    public static void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            mContext.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
           mContext.startActivity(webIntent);
        }
    }
}

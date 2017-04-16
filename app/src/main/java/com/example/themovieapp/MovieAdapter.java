package com.example.themovieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BLAQ on 4/15/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movies> MovieList;
    private Context mContext;


    public MovieAdapter(Context mContext, List<Movies> MovieList)
    {
        this.mContext = mContext;
        this.MovieList=MovieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {

         Movies movieItem = MovieList.get(position);
        Picasso.with(mContext)
                .load(movieItem.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(holder.Poster);
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView Poster;

        public MyViewHolder(View itemView) {
            super(itemView);

            Poster=(ImageView) itemView.findViewById(R.id.poster);
        }
    }


}

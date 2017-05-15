package com.example.themovieapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by BLAQ on 5/14/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FavoriteAdapter(Context context, Cursor cursor) {

        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new FavoriteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        Picasso.with(mContext)
                .load(mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_POSTER)))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.Poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent PutDataIntent = new Intent(mContext,MovieDetails.class);
                ////pass all the data to be passed
                PutDataIntent.putExtra("Backdrop",mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_BACKDROP)));
                PutDataIntent.putExtra("Poster", mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_POSTER)));
                PutDataIntent.putExtra("Title",mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_TITLE)));
                PutDataIntent.putExtra("ReleaseDate",mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_RELEASE_DATE)));
                PutDataIntent.putExtra("Rating", mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_RATING)));
                PutDataIntent.putExtra("Synopsis",mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_SYNOPOSIS)));
                PutDataIntent.putExtra("MovieID",mCursor.getString(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_ID)));
                mContext.startActivity(PutDataIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView Poster;

        public MyViewHolder(View itemView) {
            super(itemView);

            Poster=(ImageView) itemView.findViewById(R.id.poster);
        }
    }
}

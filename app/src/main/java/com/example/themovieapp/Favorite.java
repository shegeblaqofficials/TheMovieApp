package com.example.themovieapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

public class Favorite extends AppCompatActivity {

    private  RecyclerView fav_RecyclerView;
    private SQLiteDatabase mDb;
    private FavoriteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        fav_RecyclerView = (RecyclerView) findViewById(R.id.recycler_view_fav);
        fav_RecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ///CONNECT TO DB
        FavoriteDbhelper dbhelper = new FavoriteDbhelper(this);
        mDb = dbhelper.getWritableDatabase();

        Cursor cursor = getAllFavorites();

        mAdapter= new FavoriteAdapter(this,cursor);
        fav_RecyclerView.setAdapter(mAdapter);

    }

    ///get all favorite list
    private Cursor getAllFavorites(){
        return mDb.query(
                FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoriteContract.FavoriteEntry._ID
        );
    }

}
package com.example.themovieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BLAQ on 5/13/2017.
 */

public class FavoriteDbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="favorite.db";
    private static final int DATABASE_VERSION=1;

    public FavoriteDbhelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE ="CREATE TABLE " +
                FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavoriteEntry.MOVIE_POSTER + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_ID + " INTEGER NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_RATING + " DOUBLE NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_SYNOPOSIS + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.MOVIE_BACKDROP + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

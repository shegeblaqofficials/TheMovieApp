package com.example.themovieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utilities.AppConfig;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class MovieDetails extends AppCompatActivity {

    private List<Video> VideoList = new ArrayList<>();
    private VideoAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String Backdrop;
    private String Title;
    private String ReleaseDate;
    private double Rating;
    private String Synopsis;
    private int MovieID;
    private String Poster;
    private SQLiteDatabase mDb;

    @BindView(R.id.movie_title)TextView Title_tv;
    @BindView(R.id.releaseDate)TextView Release_tv;
    @BindView(R.id.rating)TextView Rating_tv;
    @BindView(R.id.synopsis)TextView Synopsis_tv;
    @BindView(R.id.backdrop)ImageView Backdrop_img;
    @BindView(R.id.fab)FloatingActionButton fav_btn;
    @BindView(R.id.progressbar_video) ProgressBar mProgressBar;
    @BindView(R.id.recycler_view_video) RecyclerView recyclerView;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private boolean MOVIE_FAV_STATUS=false;
    private String TAG="tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mAdapter = new VideoAdapter(this,VideoList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        ////make refrence to db
        FavoriteDbhelper dbhelper = new FavoriteDbhelper(this);
        mDb = dbhelper.getWritableDatabase();

        ///get the sent in data
        Intent SentData = getIntent();

        if(SentData.hasExtra("Backdrop")) {
            Backdrop = SentData.getStringExtra("Backdrop");
        }
        if(SentData.hasExtra("Poster")) {
            Poster = SentData.getStringExtra("Poster");
        }
        if(SentData.hasExtra("Title")) {
            Title = SentData.getStringExtra("Title");
        }
        if(SentData.hasExtra("ReleaseDate")) {
            ReleaseDate = SentData.getStringExtra("ReleaseDate");
        }
        if(SentData.hasExtra("Rating")){
            Rating = SentData.getDoubleExtra("Rating",0);
        }
        if(SentData.hasExtra("Synopsis")) {
            Synopsis = SentData.getStringExtra("Synopsis");
        }

        if(SentData.hasExtra("MovieID")) {
            MovieID = SentData.getIntExtra("MovieID",0);
        }

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), Backdrop);
        supportPostponeEnterTransition();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(Title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        ///Pass the data into view
        SetData(Title,ReleaseDate,Rating,Synopsis);

        //decide fabcolor and icon
        Cursor cursor = countFavorite(MovieID);
        if(cursor.getCount()>0){
            ////set status present
            MOVIE_FAV_STATUS=true;
            ///changecolor
            changeColorOn();
            ///change icon
            changeIcon(R.drawable.ic_heart);
        }

        ///fecth movie data
        FetchMovieVideos(MovieID);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    private void SetData(String Title, String ReleaseDate, double Rating, String Synopsis)
    {
        //set the back drop image with picasso
        Picasso.with(this)
                .load(Backdrop)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(Backdrop_img);

        ///set other text view
        Title_tv.setText(Title);
        Release_tv.setText(ReleaseDate);
        Rating_tv.setText(""+Rating);
        Synopsis_tv.setText(Synopsis);
    }

    public void favourite(View view) {

        if(MOVIE_FAV_STATUS==true)
        {
            ///add data to favorite
            removeFavorite(MovieID);
            //change color
            changeColorOff();
            ///change icon
            changeIcon(R.drawable.ic_heart_outline);
            ////set status false
            MOVIE_FAV_STATUS=true;
            Toast.makeText(this, "Movie has been removed from Favorite", Toast.LENGTH_SHORT).show();

        }else {
            ///add data to favorite
            addNewMovie(MovieID, Title, Poster, Rating, ReleaseDate, Synopsis, Backdrop);
            //change color
            changeColorOn();
            ///change icon
            changeIcon(R.drawable.ic_heart);
            ///set status true
            MOVIE_FAV_STATUS=true;
            Toast.makeText(this, "Movie has been added to Favorite", Toast.LENGTH_SHORT).show();
        }
    }

    public void ReadReview(View view) {
        Toast.makeText(this, "I want to read a review", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MovieDetails.this, Review.class);
                startActivity(intent);
    }

    ////method to add new Movie
    private long addNewMovie (int Movie_ID, String Title, String Poster, double Rating, String Release_Date, String Synoposis, String Backdrop){
        ContentValues cv = new ContentValues();
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_ID,Movie_ID);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_TITLE,Title);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_POSTER,Poster);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_RATING,Rating);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_RELEASE_DATE,Release_Date);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_SYNOPOSIS,Synoposis);
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_BACKDROP,Backdrop);

        return mDb.insert(FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME,null,cv);
    }


    ////method to delete favorite
    private boolean removeFavorite(int id) {
        return mDb.delete(FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME, FavoriteContract.FavoriteEntry.MOVIE_ID + "=" + id, null) > 0;
    }

    ////method to delete favorite
    private Cursor countFavorite(int movieID) {

        String [] projection = {FavoriteContract.FavoriteEntry.MOVIE_ID};
        String selection = FavoriteContract.FavoriteEntry.MOVIE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(movieID)};
        return mDb.query(
                FavoriteContract.FavoriteEntry.MOVIE_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                FavoriteContract.FavoriteEntry._ID
        );
    }

    ///method to change FAB COLOUR
    private void changeColorOn(){
        fav_btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
    }
    private void changeColorOff(){
        fav_btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF4081")));
    }

    //change fab icon
    private void changeIcon(int icon){
        fav_btn.setImageDrawable(ContextCompat.getDrawable(this,icon));
    }

    ////method to fetch from the api with volley
    public void FetchMovieVideos(int movieid) {

        String url = AppConfig.BASE_URL+valueOf(movieid)+"/videos"+AppConfig.API_KEY;

        mProgressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new

                Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ///hide loader
                        mProgressBar.setVisibility(View.GONE);
//                        ////pass data to save
//                        StoreData = response;
                        ///perepare the data
                        PrepareData(response);

                        ///hide loader
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                ///hide loader
                mProgressBar.setVisibility(View.GONE);
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    public void PrepareData(JSONObject response)
    {
        ///empty movie list first
        VideoList.clear();

        try {
            JSONArray obj = response.getJSONArray("results");

            ////for the array
            Video item;

            for (int i = 0; i < obj.length(); i++) {

                JSONObject jsonObject = obj.getJSONObject(i);

                ///get data from api json data
                String key =jsonObject.getString("key");

                ///instantiate new Movie array data
                item = new Video();

                ///set the data value into the array object
                item.setLabel(" Watch Trailer "+ (i+=1));
                item.setKey(key);

                ///set the data object into the array
                VideoList.add(item);
            }

            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}

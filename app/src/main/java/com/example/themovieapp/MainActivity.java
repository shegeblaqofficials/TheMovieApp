package com.example.themovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utilities.AppConfig;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private ProgressBar mProgressBar;
    private List<Movies> MovieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MovieAdapter(this, MovieList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        ///fetch movie posters
        FetchMovies(AppConfig.PopularBaseUrl);


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
        if (id == R.id.action_popular) {

            ///before fetch another clear list
            MovieList.clear();
            ///fetch popular movies
            FetchMovies(AppConfig.PopularBaseUrl);
        }

        if (id == R.id.action_top_rated) {

            ///before fetch another clear list
            MovieList.clear();
            ///fetch top rated movies
            FetchMovies(AppConfig.TopRateBaseUrl);
        }

        return super.onOptionsItemSelected(item);
    }


    ////method to fetch from the api with volley
    public void FetchMovies(String BaseUrl) {


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseUrl, new

                Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ///hide loader
                        mProgressBar.setVisibility(View.GONE);

                        try {
                            JSONArray obj = response.getJSONArray("results");

                            ////for the array
                            Movies item;

                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonObject = obj.getJSONObject(i);

                                ///get data from api json data
                                String PosterImage = jsonObject.getString("poster_path");
                                String Backdrop =jsonObject.getString("backdrop_path");
                                int MovieID = jsonObject.getInt("id");
                                String Title =jsonObject.getString("original_title");
                                String ReleaseDate =jsonObject.getString("release_date");
                                String Synposis =jsonObject.getString("overview");
                                double rating =jsonObject.getDouble("vote_average");

                                ///instantiate new Movie array data
                                item = new Movies();

                                ///set the data value into the array object
                                item.setPoster(AppConfig.PosterBaseUrl + PosterImage);
                                item.setBackdrop(AppConfig.PosterBigBaseUrl + Backdrop);
                                item.setMovieID(MovieID);
                                item.setTitle(Title);
                                item.setReleaseDate(ReleaseDate);
                                item.setRating(rating);
                                item.setSynopsis(Synposis);

                                ///set the data object into the array
                                MovieList.add(item);
                            }

                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
}

package com.example.themovieapp;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utilities.AppConfig;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private static final String TAG = "tag";
    private ProgressBar mProgressBar;
    private List<Movies> MovieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;

    private static final int FETCH_MOVIE_CALLBACKS =0;

    private static String QUERYURL="query";

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mLinearLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;


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

        LoaderManager.LoaderCallbacks<List<Movies>> callbacks = MainActivity.this;

        ///create bundles for the url
        Bundle urlBundle = new Bundle();
        urlBundle.putString(QUERYURL,AppConfig.PopularBaseUrl);

//        getSupportLoaderManager().initLoader(FETCH_MOVIE_CALLBACKS,urlBundle,callbacks);


        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Movies> githubSearchLoader = loaderManager.getLoader(FETCH_MOVIE_CALLBACKS);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(FETCH_MOVIE_CALLBACKS,urlBundle,callbacks);
        } else {
            loaderManager.restartLoader(FETCH_MOVIE_CALLBACKS,urlBundle,callbacks);
        }

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = MainActivity.this;

        // Get the widgets reference from XML layout
        mLinearLayout = (LinearLayout) findViewById(R.id.lv);
        // Set a click listener for the text view

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

        if (id == R.id.action_sort) {
            ///show the window
            sortPopUp();
        }
        return super.onOptionsItemSelected(item);
    }

    ////method to fetch from the api with volley
    public List<Movies> FetchMovies(String BaseUrl) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseUrl, new

                Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

        return MovieList;
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Movies>>(this) {

            List mjsonData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mjsonData!=null)
                {
                 deliverResult(mjsonData);
                }
                else
                {
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public List<Movies> loadInBackground() {

                String queryUrl = args.getString(QUERYURL);
                try {
                    URL fetchUrl = new URL(queryUrl);
                    return FetchMovies(fetchUrl.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Movies> data) {
                mjsonData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {

        if(null == data) {

            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
        }
        else{
        mAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);}

        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {

    }


    ///method for sort pop up window
    private void sortPopUp(){

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.popup,null);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton)customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER,0,0);
    }


    ////when sort is clicked
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.popular:
                if (checked)
                    // Popular top rated is clicked
                    ///before fetch another clear list
                    MovieList.clear();
                ///fetch popular movies
                ///create bundles for the url
                Bundle urlBundle = new Bundle();
                urlBundle.putString(QUERYURL,AppConfig.PopularBaseUrl.toString());
                getSupportLoaderManager().restartLoader(FETCH_MOVIE_CALLBACKS, urlBundle, this);
                    break;

            case R.id.top_rated:
                if (checked)
                    // When top rated is clicked
                    ///before fetch another clear list
                    MovieList.clear();
                ///fetch top rated movies
                ///create bundles for the url
                Bundle urlBundle_1 = new Bundle();
                urlBundle_1.putString(QUERYURL,AppConfig.TopRateBaseUrl.toString());
                getSupportLoaderManager().restartLoader(FETCH_MOVIE_CALLBACKS, urlBundle_1, this);
                    break;
        }
    }
}

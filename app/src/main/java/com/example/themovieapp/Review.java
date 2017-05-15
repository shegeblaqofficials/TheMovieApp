package com.example.themovieapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import Utilities.AppConfig;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Review extends AppCompatActivity {

    @BindView(R.id.review)TextView Review_tv;

    private static String SAVE_INSTANCE_KEY="key";

    private static String URL="Just my url";

    static final String STATE_SCORE = "10s";
    static final String STATE_LEVEL = "10s";
    private String mCurrentScore ="Real Score";
    private  String mCurrentLevel="Another score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mCurrentScore = savedInstanceState.getString(STATE_SCORE);
            mCurrentLevel = savedInstanceState.getString(STATE_LEVEL);

            Review_tv.setText("Level"+mCurrentLevel+"and Score"+mCurrentScore);
        } else {
            // Probably initialize members with default values for a new instance
            Review_tv.setText("Level 0 and Score 0");
        }


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_SCORE, mCurrentScore);
        savedInstanceState.putString(STATE_LEVEL, mCurrentLevel);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private String FetchReviw(String url)
    {
        String Review=url;

        return Review;
    }

}
